package com.remp.work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.remp.work.model.dto.Advice;
import com.remp.work.model.dto.Deprive;

/**
 * <p>
 * 렌탈관리를 위한 DaoImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Repository("rentalDao")
public class RentalDaoImpl implements RentalDao {
	
	private FactoryDao factory;
	private static RentalDaoImpl instance;
	
	public RentalDaoImpl() { }
	
	@Autowired
	public void setFactoryDao(FactoryDao factory) {
		this.factory = factory;
	}
	
	public static RentalDaoImpl getInstance() {
		if(instance == null) {
			instance = new RentalDaoImpl();
		}
		return instance;
	}

	/* ======================================== by 이동훈 ================================================= */
	   public int insertVisit(Map<String, String> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into visit values('vi'||trim(to_char(visit_seq.nextval, '0000000000')), ?, ?, ?, to_date(?,'yyyy-mm-ddhh24:mi'), ?, ?, ?, ?, ?, ?, ?, 'N')";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, map.get("sb_item"));
			pstmt.setString(2, map.get("hi_itId"));
			pstmt.setString(3, map.get("tb_cuName"));
			pstmt.setString(4, map.get("tb_reDay"));
			pstmt.setString(5, map.get("tb_mobile"));
			pstmt.setString(6, map.get("tb_post"));
			pstmt.setString(7, map.get("tb_addr"));
			pstmt.setString(8, map.get("tb_addD"));
			pstmt.setString(9, map.get("vi_code"));
			pstmt.setString(10, map.get("tb_cuId"));
			pstmt.setString(11, map.get("emId"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("insertVisit SQL Error");
			e.printStackTrace();
		}
		return result;
	}
    /* ======================================== by 이원호 ================================================= */
		@Override
		public List<Map<String, String>> selectVisitSearchList(String state, String select, String search) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			String sql = "";
			String sql2 = "select it_name from item where it_id = ?";
			int count = 0;
			List<Map<String, String>> list = new ArrayList<>();
			try {
				con = factory.getConnection();
				if(select.equals("all")) {
					sql = "SELECT * FROM visit WHERE vi_code=? ORDER BY vi_day DESC";
					count = 1;
				} else if (select.equals("product")){
					sql = "SELECT * FROM visit WHERE it_id like ? AND vi_code=?";
				} else if (select.equals("day")) {
					sql = "SELECT * FROM visit WHERE to_char(vi_day,'yyyy-mm-dd') like ? AND vi_code=?";
				} else if (select.equals("status")) {
					sql = "SELECT * FROM visit WHERE vi_complete like ? AND vi_code=?";
				} else {
					return null;
				}
				if (count == 1) {
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, state);
				} else {
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+search+"%");
					pstmt.setString(2, state);
				}
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Map<String, String> map = new HashMap<>();
					String itId = rs.getString("it_id");
					pstmt = con.prepareStatement(sql2);
					pstmt.setString(1, itId);
					rs2 = pstmt.executeQuery();
					if(rs2.next()) {
						map.put("itName", rs2.getString("it_name"));
					}
					map.put("viId", rs.getString("vi_id"));
					map.put("buyId", rs.getString("buy_id"));
					map.put("viName", rs.getString("vi_name"));
					map.put("viDay", rs.getString("vi_day"));
					map.put("viMobile", rs.getString("vi_mobile"));
					map.put("viPost", rs.getString("vi_post"));
					map.put("viAddr", rs.getString("vi_addr"));
					map.put("viAddD", rs.getString("vi_add_d"));
					map.put("viCode", rs.getString("vi_code"));
					map.put("cuId", rs.getString("cu_id"));
					map.put("emId", rs.getString("em_id"));
					map.put("viComplete", rs.getString("vi_complete"));
					list.add(map);
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
		public Map<String, String> selectBuyDay(String buyId) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT buy_start, buy_end FROM buy WHERE buy_id=?";
			Map<String, String> hashmap = new HashMap<>();
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, buyId);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					hashmap.put("buyStart", rs.getString("buy_start"));
					hashmap.put("buyEnd", rs.getString("buy_end"));
					return hashmap;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt, rs);
			}
			return null;
		}
		
		@Override
		public int insertRefundInput(Deprive depriveDto) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql1 = "SELECT it_id FROM product WHERE pr_id=?";
			String sql = "INSERT INTO input(in_id,pr_id,it_id,in_count,in_state,in_day,in_delivery,in_complete) "
					+ "VALUES ('IN'||trim(to_char(SEQ_IN_ID.nextval, '0000000000')),?,?,?,?,?,?,?)";
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql1);
				pstmt.setString(1, depriveDto.getPrId());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					String itId = rs.getString("it_id");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, depriveDto.getPrId());
					pstmt.setString(2, itId);
					pstmt.setString(3, "1");
					pstmt.setString(4, "re_return");
					pstmt.setString(5, depriveDto.getDay());
					pstmt.setString(6, depriveDto.getEngineerName());
					pstmt.setString(7, "N");
					return pstmt.executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt);
			}
			return 0;
		}
		
		@Override
		public int insertRefundResult(Deprive depriveDto) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = "INSERT INTO deprive VALUES ('de'||trim(to_char(deprive_seq.nextval,'0000000000')),?,to_date(?,'yyyy-mm-ddhh24:mi'),?,?,?,?,?,?,?)";
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, depriveDto.getViId());
				pstmt.setString(2, depriveDto.getDay());
				pstmt.setString(3, depriveDto.getCuId());
				pstmt.setString(4, depriveDto.getCuName());
				pstmt.setString(5, depriveDto.getPrId());
				pstmt.setString(6, depriveDto.getState());
				pstmt.setString(7, depriveDto.getEngineerId());
				pstmt.setString(8, depriveDto.getEngineerName());
				pstmt.setString(9, depriveDto.getContent());
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt);
			}
			return 0;
		}
		
		@Override
		public int updateRefundResult(Deprive depriveDto) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = "UPDATE deprive SET de_day=to_date(?,'yyyy-mm-ddhh24:mi'), de_content=? WHERE de_id=?";
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, depriveDto.getDay());
				pstmt.setString(2, depriveDto.getContent());
				pstmt.setString(3, depriveDto.getId());
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt);
			}
			return 0;
		}
		
		@Override
		public Map<String, String> selectRefundResult(String prId, String cuId, String viDay) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT * FROM deprive WHERE pr_id=? AND cu_id=? AND to_char(de_day,'yyyy-mm-dd')=?";
			try {
				Map<String, String> map = new HashMap<>();
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, prId);
				pstmt.setString(2, cuId);
				pstmt.setString(3, viDay);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					map.put("deId", rs.getString("de_id"));
					map.put("deDay", rs.getString("de_day"));
					map.put("deContent", rs.getString("de_content"));
					return map;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt, rs);
			}
			return null;
		}

		//상담 아이디 생성
		@Override
		public String selectAdviceId() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select 'ad'||trim(to_char(temadvice_seq.nextval,'0000000000')) AS \"temadvice\" from dual";
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					return rs.getString("temadvice");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt, rs);
			}
			return null;
		}
		
		//상담 기본 테이블 등록
		@Override
		public int insertAdvice(String adviceId, String employeeId) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = "INSERT INTO advice(ad_id, ad_day, em_id) VALUES (?,systimestamp,?)";
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, adviceId);
				pstmt.setString(2, employeeId);
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt);
			}
			return 0;
		}

		//상담 세부내용
		@Override
		public int insertAdvice(Map<String, String> map) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = "insert into advice_detail values(?, ?, ?, ?)";
			int result = 0;
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, map.get("adviceId"));
				pstmt.setString(2, map.get("sb_item"));
				pstmt.setString(3, map.get("adSort"));
				pstmt.setString(4, map.get("ta_adContent"));
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("insertAsAdvice SQL Error");
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt);
			}
			return result;
		}
		
		//구매테이블 아이디 생성
		@Override
		public String selectBuyId() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select 'BU'||trim(to_char(SEQ_BU_ID.nextval,'0000000000')) AS \"buyId\" from dual";
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					return rs.getString("buyId");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt, rs);
			}
			return null;
		}
			
		// 구매 테이블 등록
		@Override
		public int insertBuyAdvice(Map<String, String> adviceInfo) {
			Connection con = null;
			PreparedStatement pstmt = null;
			int count = 0;
			String sql = "";
			if (adviceInfo.get("sel_card").length() > 0) {
				sql = "INSERT INTO buy(buy_id,buy_day,cu_id,it_id,buy_price,buy_count,buy_start,buy_end,buy_post,buy_addr,buy_add_d,buy_method,buy_pay,buy_ccompany,buy_card,buy_account,buy_total,buy_complete,buy_debt) "
						+ "VALUES (?,systimestamp,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				count = 1;
			} else if(adviceInfo.get("sel_account").length() > 0) {
				sql = "INSERT INTO buy(buy_id,buy_day,cu_id,it_id,buy_price,buy_count,buy_start,buy_end,buy_post,buy_addr,buy_add_d,buy_method,buy_pay,buy_cbank,buy_transfer,buy_account,buy_total,buy_complete,buy_debt) "
						+ "VALUES (?,systimestamp,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				count = 2;
			} else {
				sql = "INSERT INTO buy(buy_id,buy_day,cu_id,it_id,buy_price,buy_count,buy_start,buy_end,buy_post,buy_addr,buy_add_d,buy_method,buy_pay,buy_account,buy_total,buy_complete,buy_debt) "
						+ "VALUES (?,systimestamp,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				count = 3;
			}
			
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, adviceInfo.get("sb_item"));
				pstmt.setString(2, adviceInfo.get("tb_cuId"));
				pstmt.setString(3, adviceInfo.get("tb_itId"));
				pstmt.setInt(4, Integer.parseInt(adviceInfo.get("tb_itPrice")));
				pstmt.setInt(5, Integer.parseInt(adviceInfo.get("tb_itNumber")));
				pstmt.setString(6, adviceInfo.get("tb_start"));
				pstmt.setString(7, adviceInfo.get("tb_end"));
				pstmt.setString(8, adviceInfo.get("tb_post"));
				pstmt.setString(9, adviceInfo.get("tb_addr"));
				pstmt.setString(10, adviceInfo.get("tb_addD"));
				pstmt.setString(11, adviceInfo.get("rb_cashMethod"));
				pstmt.setString(12, adviceInfo.get("rb_timeMethod"));
				if(count == 1) {
					pstmt.setString(13, adviceInfo.get("sel_card"));
					pstmt.setString(14, adviceInfo.get("tb_payNum"));
					pstmt.setString(15, "110-254-256841"); //account
					pstmt.setInt(16, Integer.parseInt(adviceInfo.get("tb_itPrice"))*Integer.parseInt(adviceInfo.get("tb_itNumber"))); //total
					pstmt.setString(17, "N");
					pstmt.setInt(18, Integer.parseInt(adviceInfo.get("tb_itPrice"))*Integer.parseInt(adviceInfo.get("tb_itNumber")));
				} else if(count == 2) {
					pstmt.setString(13, adviceInfo.get("sel_account"));
					pstmt.setString(14, adviceInfo.get("tb_payNum"));
					pstmt.setString(15, "110-254-256841"); //account
					pstmt.setInt(16, Integer.parseInt(adviceInfo.get("tb_itPrice"))*Integer.parseInt(adviceInfo.get("tb_itNumber"))); //total
					pstmt.setString(17, "N");
					pstmt.setInt(18, Integer.parseInt(adviceInfo.get("tb_itPrice"))*Integer.parseInt(adviceInfo.get("tb_itNumber")));
				} else {
					pstmt.setString(13, "110-254-256841"); //account
					pstmt.setInt(14, Integer.parseInt(adviceInfo.get("tb_itPrice"))*Integer.parseInt(adviceInfo.get("tb_itNumber"))); //total
					pstmt.setString(15, "N");
					pstmt.setInt(16, Integer.parseInt(adviceInfo.get("tb_itPrice"))*Integer.parseInt(adviceInfo.get("tb_itNumber")));
				}
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt);
			}
			return 0;
		}

		// 렌탈 상담 시 출고 테이블 등록
		@Override
		public int insertOutput(Map<String, String> adviceInfo) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = "INSERT INTO output (ou_id, it_id, ou_state, ou_out_day, ou_count, ou_complete) VALUES ('ou'||trim(to_char(output_seq.nextval,'0000000000')),?,?,systimestamp,?,?)";
			try {
				con = factory.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, adviceInfo.get("tb_itId"));
				pstmt.setString(2, "re_output");
				pstmt.setInt(3, Integer.parseInt(adviceInfo.get("tb_itNumber")));
				pstmt.setString(4, "N");
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				factory.close(con, pstmt);
			}
			return 0;
		}
	}