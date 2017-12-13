package com.remp.work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.remp.work.model.dto.Employee;

/**
 * <p>
 * 직원관리를 위한 DaoImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Repository("memberDao")
public class MemberDaoImpl implements MemberDao{
	private static MemberDaoImpl instance;
	private MemberDaoImpl() {
		
	}
	public static MemberDaoImpl getInstance() {
		if(instance == null) {
			instance = new MemberDaoImpl();
		}
		return instance;
	}
	private FactoryDao factory;
	@Autowired
	public void setFactoryDao(FactoryDao factory) {
		this.factory = factory;
	}
	
	/* ======================================== by 이동훈 ================================================= */
	@Override
	public Employee selectMemberInfoSub(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from employee where em_id=?";
		Employee emp = null;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				emp = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8), rs.getString(9));
			}
		} catch (SQLException e) {
			System.out.println("selectMemberInfo SQL Error");
		} finally {
			factory.close(con, pstmt, rs);
		}
		return emp;
	}
	
	@Override
	public int updateMemberInfoSub(Map<String, String> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update employee set em_id=?, em_pw=?, em_name=?, em_mobile=?, em_work=?, em_entry_date=to_date(?, 'yyyy-mm-dd'), "
				+ "em_state=?, em_update=? where em_id=?";
		int result = 0;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ("yyyy.MM.dd", Locale.KOREA);
		Date currentTime = new Date ();
		String mTime = mSimpleDateFormat.format ( currentTime );
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, map.get("tb_id"));
			pstmt.setString(2, map.get("tb_pw"));
			pstmt.setString(3, map.get("tb_name"));
			pstmt.setString(4, map.get("tb_mobile"));
			pstmt.setString(5, map.get("tb_work"));
			pstmt.setString(6, map.get("tb_entryDate"));
			pstmt.setString(7, map.get("sb_state"));
			pstmt.setString(8, mTime);
			pstmt.setString(9, map.get("tb_id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("updateMemberInfo SQL Error");
		} finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	/* ======================================== by 이원호 ================================================= */
	@Override 
	public String selectRandomEmpl() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT LPAD(empl_seq.NEXTVAL,4,0) AS \"code\" FROM DUAL";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Calendar date = Calendar.getInstance();
				String newId = date.get(Calendar.YEAR)+rs.getString("code");
				return newId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}

	@Override
	public HashMap<String, String> selectLogin(String id, String pw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT em_id, em_name, au_id FROM employee WHERE em_id=? AND em_pw=?";
		try {
			HashMap<String, String> hashmap = new HashMap<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hashmap.put("loginId", rs.getString("em_id"));
				hashmap.put("loginName", rs.getString("em_name"));
				hashmap.put("authority", rs.getString("au_id"));
			}
			return hashmap;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	//관리자 직원 조회
	@Override
	public List<Map<String, String>> selectMemberList(String searchId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT em_id, em_name FROM employee WHERE em_id like ?";
		try {
			List<Map<String, String>> list = new ArrayList<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+searchId+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String, String> hashmap = new HashMap<>();
				hashmap.put("memId", rs.getString("em_id"));
				hashmap.put("memName", rs.getString("em_name"));
				list.add(hashmap);
			}
			return list;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	@Override
	public List<Map<String, String>> selectAuthorityList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT au_id, au_name FROM authority";
		try {
			List<Map<String, String>> list = new ArrayList<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String, String> hashmap = new HashMap<>();
				hashmap.put("memAuId", rs.getString("au_id"));
				hashmap.put("memAuName", rs.getString("au_name"));
				list.add(hashmap);
			}
			return list;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	@Override
	public Map<String, String> selectMemberInfo(String memberId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM employee WHERE em_id=?";
		try {
			Map<String, String> hashmap = new HashMap<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hashmap.put("memId", rs.getString("em_id"));
				hashmap.put("memPw", rs.getString("em_pw"));
				hashmap.put("memName", rs.getString("em_name"));
				hashmap.put("memMobile", rs.getString("em_mobile"));
				hashmap.put("memWork", rs.getString("em_work"));
				hashmap.put("memEntry", rs.getString("em_entry_date"));
				hashmap.put("memState", rs.getString("em_state"));
				hashmap.put("memUpdate", rs.getString("em_update"));
				hashmap.put("memAuId", rs.getString("au_id"));
			}
			return hashmap;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}

	@Override
	public int insertMemberJoin(Map<String, String> memberJoin) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,to_char(systimestamp,'yyyy-mm-dd'))";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberJoin.get("tb_memId"));
			pstmt.setString(2, memberJoin.get("tb_memPw"));
			pstmt.setString(3, memberJoin.get("tb_memName"));
			pstmt.setString(4, memberJoin.get("tb_memMobile"));
			pstmt.setString(5, memberJoin.get("tb_memWork"));
			pstmt.setString(6, memberJoin.get("tb_memWork"));
			pstmt.setString(7, memberJoin.get("tb_auId"));
			pstmt.setString(8, memberJoin.get("tb_memState"));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}
	
	@Override
	public int updateMemberInfo(Map<String, String> memberInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE employee SET em_pw=?, em_name=?, em_mobile=?, em_work=?, em_state=?, em_update=to_char(systimestamp,'yyyy-mm-dd'), au_id=?, em_entry_date=? WHERE em_id=?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberInfo.get("tb_memPw"));
			pstmt.setString(2, memberInfo.get("tb_memName"));
			pstmt.setString(3, memberInfo.get("tb_memMobile"));
			pstmt.setString(4, memberInfo.get("tb_memWork"));
			pstmt.setString(5, memberInfo.get("tb_memState"));
			pstmt.setString(6, memberInfo.get("tb_auId"));
			pstmt.setString(7, memberInfo.get("tb_memEntry"));
			pstmt.setString(8, memberInfo.get("tb_memId"));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}
	
	@Override
	public int updateMemberInfoDetail(Map<String, String> memberInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = factory.getConnection();
			if (memberInfo.get("column").equals("1")) {
				sql = "UPDATE employee SET em_pw=?, em_update=to_char(systimestamp,'yyyy-mm-dd') WHERE em_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberInfo.get("tb_memPw"));
				pstmt.setString(2, memberInfo.get("tb_memId"));
			} else if(memberInfo.get("column").equals("2")) {
				sql = "UPDATE employee SET em_name=?, em_update=to_char(systimestamp,'yyyy-mm-dd') WHERE em_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberInfo.get("tb_memName"));
				pstmt.setString(2, memberInfo.get("tb_memId"));
			} else if(memberInfo.get("column").equals("3")) {
				sql = "UPDATE employee SET em_mobile=?, em_update=to_char(systimestamp,'yyyy-mm-dd') WHERE em_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberInfo.get("tb_memMobile"));
				pstmt.setString(2, memberInfo.get("tb_memId"));
			} else if(memberInfo.get("column").equals("4")) {
				sql = "UPDATE employee SET em_work=?, em_update=to_char(systimestamp,'yyyy-mm-dd') WHERE em_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberInfo.get("tb_memWork"));
				pstmt.setString(2, memberInfo.get("tb_memId"));
			} else if(memberInfo.get("column").equals("5")) {
				sql = "UPDATE employee SET em_state=?, em_update=to_char(systimestamp,'yyyy-mm-dd') WHERE em_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberInfo.get("tb_memState"));
				pstmt.setString(2, memberInfo.get("tb_memId"));
			} else if(memberInfo.get("column").equals("6")) {
				sql = "UPDATE employee SET au_id=?, em_update=to_char(systimestamp,'yyyy-mm-dd') WHERE em_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberInfo.get("tb_auId"));
				pstmt.setString(2, memberInfo.get("tb_memId"));
			} else if(memberInfo.get("column").equals("7")) {
				sql = "UPDATE employee SET em_entry_date=?, em_update=to_char(systimestamp,'yyyy-mm-dd') WHERE em_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberInfo.get("tb_memEntry"));
				pstmt.setString(2, memberInfo.get("tb_memId"));
			} else {
				System.out.println("없단다");
				return 0;
			}
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}
}