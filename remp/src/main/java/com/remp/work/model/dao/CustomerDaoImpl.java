package com.remp.work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 고객관리를 위한 DaoImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao{
	private static CustomerDaoImpl instance;
	private  CustomerDaoImpl() {
		
	}
	public static CustomerDaoImpl getInstance() {
		if(instance == null) {
			instance = new CustomerDaoImpl();
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
	public String selectPw(String id, String name, String birth, String mobile) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select cu_id from customer where cu_id=? and cu_name=? and cu_birth=? and cu_mobile=?";
		String email = null;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, birth);
			pstmt.setString(4, mobile);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				email = rs.getString("cu_id");
			}
		} catch (SQLException e) {
			System.out.println("findPw SQL Error");
		} finally {
			factory.close(con, pstmt, rs);
		}
		return email;
	}
	
	@Override
	public int updateTmpPw(String id, String tmpPW) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update customer set cu_pw=? where cu_id=?";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tmpPW);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("tmpPw SQL Error");
		} finally {
			factory.close(con, pstmt);;
		}
		return result;
	}
	
	@Override
	public List<Map<String, String>> selectItemInfo(String customerId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from buy where cu_id=? and buy_complete='Y'";
		List<Map<String, String>> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("buyId", rs.getString("buy_id"));
				map.put("itId", rs.getString("it_id"));
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("selectItemInfo SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	@Override
	public Map<String, String> selectChangeInfo(String sbValue) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from buy where buy_id=? and buy_complete='Y'";
		Map<String, String> map = new HashMap<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sbValue);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				map.put("buyStart", rs.getString("buy_start"));
				map.put("buyEnd", rs.getString("buy_end"));
			}
		} catch (SQLException e) {
			System.out.println("selectChangeInfo SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return map;
	}
	
	/* ======================================== by 김재림 ================================================= */
	public String selectCustomerId(Map<String, String> memberinfo) {
		StringBuilder returnValue = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="select cu_id from customer where cu_name = ? and cu_mobile = ?";
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberinfo.get("name"));
			pstmt.setString(2, memberinfo.get("phone"));
			rs = pstmt.executeQuery();
			rs.next();
			returnValue.append(rs.getString("cu_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue.toString();
	}
	
	public int UpdateUserPassword(Map<String, String> info) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println(info.get("new_pw"));
		System.out.println(info.get("id"));
		System.out.println(info.get("password"));
		
		String sql = "update customer set CU_PW = ? where CU_ID = ? and CU_PW = ?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.get("new_pw"));
			pstmt.setString(2, info.get("id"));
			pstmt.setString(3, info.get("password"));
			returnValue = pstmt.executeUpdate();
			System.out.println(returnValue);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}
	
	public int UpdateUserMobile(Map<String, String> info) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update customer set CU_MOBILE = ? where CU_ID = ? and CU_PW = ?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.get("new_mobile"));
			pstmt.setString(2, info.get("id"));
			pstmt.setString(3, info.get("password"));
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}
	
	public int UpdateUserAddress(Map<String, String> info) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update customer set CU_POST = ?, CU_ADDR = ?, CU_ADD_D = ? where CU_ID = ? and CU_PW = ?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.get("new_post"));
			pstmt.setString(2, info.get("new_addr"));
			pstmt.setString(3, info.get("new_addr_d"));
			pstmt.setString(4, info.get("id"));
			pstmt.setString(5, info.get("password"));
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}
	
	public int UpdateUserCard(Map<String, String> info) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update customer set CU_CARD = ?, CU_CCOMPANY = ? where CU_ID = ? and CU_PW = ?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.get("new_card"));
			pstmt.setString(2, info.get("new_c_company"));
			pstmt.setString(3, info.get("id"));
			pstmt.setString(4, info.get("password"));
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}
	
	public int UpdateUserAccount(Map<String, String> info) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update customer set CU_CBANK = ?, CU_ACCOUNT = ? where CU_ID = ? and CU_PW = ?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.get("new_a_company"));
			pstmt.setString(2, info.get("new_account"));
			pstmt.setString(3, info.get("id"));
			pstmt.setString(4, info.get("password"));
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}

	public Map<String, String> selectUserInfo(String id) {
		Map<String, String> returnValue = new HashMap<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select CU_ID, CU_NAME, CU_BIRTH, CU_MOBILE, CU_POST, CU_ADDR, CU_ADD_D, CU_CCOMPANY, CU_CARD, CU_CBANK, CU_ACCOUNT from customer where CU_ID = ?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returnValue.put("id", rs.getString("CU_ID"));
				returnValue.put("name", rs.getString("CU_NAME"));
				returnValue.put("birth", rs.getString("CU_BIRTH"));
				returnValue.put("mobile", rs.getString("CU_MOBILE"));
				returnValue.put("post", rs.getString("CU_POST"));
				returnValue.put("addr", rs.getString("CU_ADDR"));
				returnValue.put("addrd", rs.getString("CU_ADD_D"));
				returnValue.put("cCompany", rs.getString("CU_CCOMPANY"));
				String[] card = (rs.getString("CU_CARD") != null)? rs.getString("CU_CARD").split("-") : null;
				if (card != null) {
					if (card.length == 1) {
						returnValue.put("card1", card[0].substring(0, 4));
						returnValue.put("card2", card[0].substring(4, 8));
						returnValue.put("card3", card[0].substring(8, 12));
						returnValue.put("card4", card[0].substring(12, 16));
					} else {
						returnValue.put("card1", card[0]);
						returnValue.put("card2", card[1]);
						returnValue.put("card3", card[2]);
						returnValue.put("card4", card[3]);
					}
				}
				returnValue.put("aCompany", rs.getString("CU_CBANK"));
				returnValue.put("account", rs.getString("CU_ACCOUNT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return returnValue;
	}
	
	/* ======================================== by 이원호 ================================================= */
	@Override
	public HashMap<String, String> selectLogin(String id, String pw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cu_id, cu_name FROM customer WHERE cu_id=? AND cu_pw=?";
		try {
			HashMap<String, String> hashmap = new HashMap<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hashmap.put("loginId", rs.getString("cu_id"));
				hashmap.put("loginName", rs.getString("cu_name"));
			}
			return hashmap;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	//아이디 중복 확인
	@Override
	public boolean selectIdCheck(String customerId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cu_id FROM customer WHERE cu_id=?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId.trim());
			rs = pstmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return false;
	}

	//회원가입
	@Override
	public int insertJoin(Map<String, String> customerJoin) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?,?,?,to_char(systimestamp,'yyyy-mm-dd'),?,to_char(systimestamp,'yyyy-mm-dd'))";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerJoin.get("tb_id"));
			pstmt.setString(2, customerJoin.get("tb_pw"));
			pstmt.setString(3, customerJoin.get("tb_name"));
			pstmt.setString(4, customerJoin.get("tb_birth"));
			pstmt.setString(5, customerJoin.get("tb_mobile"));
			pstmt.setString(6, customerJoin.get("tb_post"));
			pstmt.setString(7, customerJoin.get("tb_addr"));
			pstmt.setString(8, customerJoin.get("tb_addD"));
			pstmt.setString(9, customerJoin.get("sel_card"));
			pstmt.setString(10, customerJoin.get("tb_card1")+customerJoin.get("tb_card2")+customerJoin.get("tb_card3")+customerJoin.get("tb_card4"));
			pstmt.setString(11, customerJoin.get("sel_account"));
			pstmt.setString(12, customerJoin.get("tb_account"));
			pstmt.setString(13, "u");
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}

	//관리자 회원 검색
	@Override
	public List<Map<String, String>> selectCustomerList(String searchId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cu_id, cu_name FROM customer WHERE cu_id like ?";
		try {
			List<Map<String, String>> list = new ArrayList<>();
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+searchId+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String, String> hashmap = new HashMap<>();
				hashmap.put("memId", rs.getString("cu_id"));
				hashmap.put("memName", rs.getString("cu_name"));
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
	
	//회원 정보
	@Override
	public Map<String, String> selectCustomerInfo(String customerId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM customer WHERE cu_id=?";
		Map<String, String> hashmap = new HashMap<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hashmap.put("memId", rs.getString("cu_id"));
				hashmap.put("memPw", rs.getString("cu_pw"));
				hashmap.put("memName", rs.getString("cu_name"));
				hashmap.put("memBirth", rs.getString("cu_birth"));
				hashmap.put("memMobile", rs.getString("cu_mobile"));
				hashmap.put("memPost", rs.getString("cu_post"));
				hashmap.put("memAddr", rs.getString("cu_addr"));
				hashmap.put("memAddD", rs.getString("cu_add_d"));
				hashmap.put("memCardSel", rs.getString("CU_CCOMPANY"));
				hashmap.put("memCard", rs.getString("cu_card"));
				hashmap.put("memAccountSel", rs.getString("CU_CBANK"));
				hashmap.put("memAccount", rs.getString("cu_account"));
				hashmap.put("memEntry", rs.getString("cu_entry_date"));
				hashmap.put("memState", rs.getString("cu_state"));
				hashmap.put("memUpdate", rs.getString("cu_update"));
			}
			return hashmap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return hashmap;
	}

	//관리자 회원 정보 전체 변경
	@Override
	public int updateCustomerInfo(Map<String, String> customerInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE customer SET cu_pw=?, cu_name=?, cu_mobile=?, cu_post=?, cu_addr=?, cu_add_d=?, cu_card=?, cu_account=?, cu_state=?, cu_update=to_char(systimestamp,'yyyy-mm-dd'), cu_ccompany=?, cu_cbank=? WHERE cu_id=?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerInfo.get("tb_memPw"));
			pstmt.setString(2, customerInfo.get("tb_memName"));
			pstmt.setString(3, customerInfo.get("tb_memMobile"));
			pstmt.setString(4, customerInfo.get("tb_post"));
			pstmt.setString(5, customerInfo.get("tb_addr"));
			pstmt.setString(6, customerInfo.get("tb_addD"));
			pstmt.setString(7, customerInfo.get("tb_card1")+customerInfo.get("tb_card2")+customerInfo.get("tb_card3")+customerInfo.get("tb_card4"));
			pstmt.setString(8, customerInfo.get("tb_memAccount"));
			pstmt.setString(9, customerInfo.get("tb_memState"));
			pstmt.setString(10, customerInfo.get("sel_card"));
			pstmt.setString(11, customerInfo.get("sel_account"));
			pstmt.setString(12, customerInfo.get("tb_memId"));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}
	
	//관리자 회원 정보 부분 변경
	@Override
	public int updateCustomerInfoDetail(Map<String, String> customerInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = factory.getConnection();
			if (customerInfo.get("column").equals("1")) {
				sql = "UPDATE customer SET cu_pw=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_memPw"));
				pstmt.setString(2, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("2")) {
				sql = "UPDATE customer SET cu_name=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_memName"));
				pstmt.setString(2, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("3")) {
				sql = "UPDATE customer SET cu_mobile=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_memMobile"));
				pstmt.setString(2, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("5")) {
				sql = "UPDATE customer SET cu_post=?, cu_addr=?, cu_add_d=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_post"));
				pstmt.setString(2, customerInfo.get("tb_addr"));
				pstmt.setString(3, customerInfo.get("tb_addD"));
				pstmt.setString(4, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("4")) {
				sql = "UPDATE customer SET cu_card=?, cu_ccompany=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_card1")+customerInfo.get("tb_card2")+customerInfo.get("tb_card3")+customerInfo.get("tb_card4"));
				pstmt.setString(2, customerInfo.get("sel_card"));
				pstmt.setString(3, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("6")) {
				sql = "UPDATE customer SET cu_state=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_memState"));
				pstmt.setString(2, customerInfo.get("tb_memId"));
			} else if(customerInfo.get("column").equals("7")) {
				sql = "UPDATE customer SET cu_cbank=?, cu_account=?, cu_update=to_char(systimestamp,'yyyy-mm-dd') WHERE cu_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customerInfo.get("tb_card1")+customerInfo.get("tb_card2")+customerInfo.get("tb_card3")+customerInfo.get("tb_card4"));
				pstmt.setString(2, customerInfo.get("sel_account"));
				pstmt.setString(3, customerInfo.get("tb_memId"));
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

	//임시 회원가입
	@Override
	public int insertTempCustomer(Map<String, String> tempInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO customer (cu_id, cu_pw, cu_name, cu_birth, cu_mobile, cu_entry_date, cu_state, cu_update) "
				+ "VALUES ('te'||trim(to_char(temcustomer_seq.nextval,'000000')),?,?,?,?,to_char(systimestamp,'yyyy-mm-dd'),'u',to_char(systimestamp,'yyyy-mm-dd'))";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tempInfo.get("memberMobile"));
			pstmt.setString(2, tempInfo.get("memberName"));
			pstmt.setString(3, tempInfo.get("memberBirth"));
			pstmt.setString(4, tempInfo.get("memberMobile"));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return 0;
	}

	//임시 회원 아이디 가져오기
	@Override
	public String selectTempInfo(Map<String, String> tempInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cu_id FROM customer WHERE cu_name=? AND cu_mobile=? AND cu_birth=?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tempInfo.get("memberName"));
			pstmt.setString(2, tempInfo.get("memberMobile"));
			pstmt.setString(3, tempInfo.get("memberBirth"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("cu_id");
			}
			System.out.println("No");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	/* ======================================== by 이민정 ================================================= */
	public boolean updatePassword(String id, String pw, String newPw) {
		Connection con = null;
		String sql = "update CUSTOMER set CU_PW=? where CU_PW=? and CU_ID=?";
		PreparedStatement pstmt = null;
		int result;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newPw);
			pstmt.setString(2, pw);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt,null);
		}
		return false;
	}

}
