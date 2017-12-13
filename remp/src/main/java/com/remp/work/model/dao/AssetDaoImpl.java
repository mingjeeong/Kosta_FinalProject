package com.remp.work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.remp.work.model.dto.Fix;
import com.remp.work.model.dto.Product;
import com.remp.work.util.RempUtility;

/**
 * <p>
 * 자산관리를 위한 DaoImpl
 * </p>
 * @author 이동훈, 김재림, 이원호, 이민정
 * @since JDK 1.8 이후, Spring F/W 3.2.12이후
 * @version ReMP v 1.0
 */
@Repository("assetDao")
public class AssetDaoImpl implements AssetDao {
	private static AssetDaoImpl instance;
	private  AssetDaoImpl() {
		
	}
	public static AssetDaoImpl getInstance() {
		if(instance == null) {
			instance = new AssetDaoImpl();
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
	public int insertFix(Map<String, String> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into fix values('fix'||trim(to_char(fix_seq.nextval, '0000000000')), ?, to_date(?, 'yyyy-mm-ddhh24:mi'), ?, ?, ?, ?, ?, ?, ?, ?)";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, map.get("hi_viId"));
			pstmt.setString(2, map.get("hi_outDay"));
			pstmt.setString(3, map.get("hi_cuId"));
			pstmt.setString(4, map.get("hi_viName"));
			pstmt.setString(5, map.get("hi_prId"));
			pstmt.setString(6, map.get("hi_outState"));
			pstmt.setString(7, map.get("engineerId"));
			pstmt.setString(8, map.get("engineerName"));
			pstmt.setString(9, map.get("hi_outSort"));
			pstmt.setString(10, map.get("hi_outContent"));
			int complete = updateViComplete(map.get("hi_viId"));
			if(complete != 0) {
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("insertFix SQL Error");
		} finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	@Override
	public int updateFix(Map<String, String> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql ="update fix set fix_id = ?, vi_id = ?, fix_day = to_date(?, 'yyyy-mm-ddhh24:mi'), cu_id = ?, cu_name = ?, pr_id = ?, fix_state = ?, "
				+ "fix_engineer_id = ?, fix_engineer_name = ?, fix_sort = ?, fix_content = ? where fix_id = ?";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, map.get("hi_outId"));
			pstmt.setString(2, map.get("hi_viId"));
			pstmt.setString(3, map.get("hi_outDay"));
			pstmt.setString(4, map.get("hi_cuId"));
			pstmt.setString(5, map.get("hi_viName"));
			pstmt.setString(6, map.get("hi_prId"));
			pstmt.setString(7, map.get("hi_outState"));
			pstmt.setString(8, map.get("engineerId"));
			pstmt.setString(9, map.get("engineerName"));
			pstmt.setString(10, map.get("hi_outSort"));
			pstmt.setString(11, map.get("hi_outContent"));
			pstmt.setString(12, map.get("hi_outId"));
			int complete = updateViComplete(map.get("hi_viId"));
			if(complete != 0) {
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("updateFix SQL Error");
		} finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	@Override
	public int updateState(String state, String buyId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql="update product set pr_state = ? where buy_id = ?";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, state);
			pstmt.setString(2, buyId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("updateState SQL Error");
		} finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	@Override
	public int updateInState(Map<String, String> map) {
		Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql1 = "SELECT it_id FROM product WHERE pr_id=?";
	      String sql = "INSERT INTO input(in_id,pr_id,it_id,in_count,in_state,in_day,in_delivery,in_complete) "
	            + "VALUES ('IN'||trim(to_char(SEQ_IN_ID.nextval, '0000000000')),?,?,?,?,?,?,?)";
	      try {
	         con = factory.getConnection();
	         pstmt = con.prepareStatement(sql1);
	         pstmt.setString(1, map.get("hi_prId"));
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	        	if(map.get("hi_outState").equals("re_return")) {
	        		String itId= rs.getString("it_id");
	        		pstmt = con.prepareStatement(sql);
	        		pstmt.setString(1, map.get("hi_prId"));
	        		pstmt.setString(2, itId);
	        		pstmt.setString(3, "1");
	        		pstmt.setString(4, "re_return");
	        		pstmt.setString(5, map.get("hi_outDay"));
	        		pstmt.setString(6, map.get("engineerName"));
	        		pstmt.setString(7, "N");
	        	}
	            return pstmt.executeUpdate();
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         factory.close(con, pstmt, rs);
	      }
	      return 0;
	}

	@Override
	public Map<String, String> selectItemName(String buyId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT i.it_name, i.it_code, p.pr_id, i.it_manufacturer FROM item i, product p WHERE i.it_id=p.it_id AND p.buy_id=?";
		Map<String, String> map = new HashMap<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, buyId.trim());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				map.put("itName", rs.getString("it_name"));
				map.put("itCode", rs.getString("it_code"));
				map.put("prId", rs.getString("pr_id"));
				map.put("prManufacturer", rs.getString("it_manufacturer"));
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	@Override
	public Map<String, String> selectVisitInfo(String visitId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from visit where vi_id = ?";
		Map<String, String> map = new HashMap<>();
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, visitId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
		            map.put("viName", rs.getString("vi_name"));
		            map.put("viDay", rs.getString("vi_day"));
		            map.put("viMobile", rs.getString("vi_mobile"));
		            map.put("cuId", rs.getString("cu_id"));
		            map.put("viPost", rs.getString("vi_post"));
		            map.put("viAddr", rs.getString("vi_addr"));
		            map.put("viAddD", rs.getString("vi_add_d"));
		            map.put("viComplete", rs.getString("vi_complete"));
		            map.put("viCode", rs.getString("vi_code"));
			}
		} catch (SQLException e) {
			System.out.println("selectVisitInfo SQL Error");
		} finally {
			factory.close(con, pstmt, rs);
		}
		return map;
	}
	
	@Override
	public Map<String, String> selectOutderId(String viCode, String visitId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int code = 0;
		Map<String, String> map = new HashMap<>();
		if (viCode.equals("As")) {
			sql = "SELECT * FROM fix WHERE vi_id=?";
			code = 1;
		} else if (viCode.equals("Refund")) {
			sql = "SELECT * FROM deprive WHERE vi_id=?";
			code = 2;
		}
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, visitId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(code == 1) {
					map.put("outId", rs.getString("fix_id"));
					map.put("outState", rs.getString("fix_state"));
					map.put("outContent", rs.getString("fix_content"));
					map.put("outSort", rs.getString("fix_sort"));
					map.put("outDay", rs.getString("fix_day"));
				} else {
					map.put("outId", rs.getString("de_id"));
					map.put("outDay", rs.getString("de_day"));
					map.put("outContent", rs.getString("de_content"));
					map.put("outState", "");
					map.put("outSort", "");
				}
				return map;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("selectOutderId SQL Error");
		}finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	
	@Override
	public int updateViComplete(String viId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update visit set vi_complete = 'Y' where vi_id = ?";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, viId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("updateViComplete SQL Error");
		}finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	@Override
	public List<Map<String, String>> selectProduct(String keyword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<Map<String, String>> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			if(keyword.trim().equals("") || keyword == null) {
				sql = "select nvl(pr_id, '-') \"pr_id\", nvl(buy_id, '-') \"buy_id\", nvl(it_id, '-') \"it_id\", nvl(it_name, '-') \"it_name\", nvl(pr_first_day, to_date('111111111111', 'yyyy-mm-ddhh24:mi')) \"pr_first_day\", "
						+ "nvl(pr_in_day, to_date('111111111111', 'yyyy-mm-ddhh24:mi')) \"pr_in_day\", nvl(pr_out_day, to_date('111111111111', 'yyyy-mm-ddhh24:mi')) \"pr_out_day\", nvl(pr_out_target, '-') \"pr_out_target\", nvl(pr_location, '-') \"pr_location\", "
						+ "nvl(pr_needs, '-') \"pr_needs\", nvl(pr_state, '-') \"pr_state\", nvl(pr_count, 0) \"pr_count\", nvl(pr_qr, '-') \"pr_qr\" from product";
				pstmt = con.prepareStatement(sql);
			} else {
				sql = "select nvl(pr_id, '-') \"pr_id\", nvl(buy_id, '-') \"buy_id\", nvl(it_id, '-') \"it_id\", nvl(it_name, '-') \"it_name\", nvl(pr_first_day, to_date('111111111111', 'yyyy-mm-ddhh24:mi')) \"pr_first_day\", "
						+ "nvl(pr_in_day, to_date('111111111111', 'yyyy-mm-ddhh24:mi')) \"pr_in_day\", nvl(pr_out_day, to_date('111111111111', 'yyyy-mm-ddhh24:mi')) \"pr_out_day\", nvl(pr_out_target, '-') \"pr_out_target\", nvl(pr_location, '-') \"pr_location\", "
						+ "nvl(pr_needs, '-') \"pr_needs\", nvl(pr_state, '-') \"pr_state\", nvl(pr_count, 0) \"pr_count\", nvl(pr_qr, '-') \"pr_qr\"  "
						+ "from product where pr_id like ? or buy_id like ? or it_id like ? or it_name like ? or "
						+ "pr_first_day like ? or pr_in_day like ? or pr_out_day like ? or  pr_out_target like ? or "
						+ "pr_location like ? or pr_needs like ? or pr_state like ? or pr_count like ? or pr_qr like ? order by pr_id";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword.trim()+"%");
				pstmt.setString(2, "%"+keyword.trim()+"%");
				pstmt.setString(3, "%"+keyword.trim()+"%");
				pstmt.setString(4, "%"+keyword.trim()+"%");
				pstmt.setString(5, "%"+keyword.trim()+"%");
				pstmt.setString(6, "%"+keyword.trim()+"%");
				pstmt.setString(7, "%"+keyword.trim()+"%");
				pstmt.setString(8, "%"+keyword.trim()+"%");
				pstmt.setString(9, "%"+keyword.trim()+"%");
				pstmt.setString(10, "%"+keyword.trim()+"%");
				pstmt.setString(11, "%"+keyword.trim()+"%");
				pstmt.setString(12, "%"+keyword.trim()+"%");
				pstmt.setString(13, "%"+keyword.trim()+"%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("prId", rs.getString("pr_id"));
				map.put("buyId", rs.getString("buy_id"));
				map.put("itId", rs.getString("it_id"));
				map.put("itName", rs.getString("it_name"));
				map.put("prFirstDay", rs.getString("pr_first_day"));
				map.put("prInDay", rs.getString("pr_in_day"));
				map.put("prOutDay", rs.getString("pr_out_day"));
				map.put("prOutTarget", rs.getString("pr_out_target"));
				map.put("prLocation", rs.getString("pr_location"));
				map.put("prNeeds", rs.getString("pr_needs"));
				map.put("prState", rs.getString("pr_state"));
				map.put("prCount", rs.getString("pr_count"));
				map.put("prQr", rs.getString("pr_qr"));
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("selectProduct SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	@Override
	public Map<String, String> selectProductUpdate(String prId, String itId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from product where pr_id = ? and it_id = ?";
		Map<String, String> map = new HashMap<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, prId);
			pstmt.setString(2, itId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				map.put("prId", rs.getString("pr_id"));
				map.put("prInDay", rs.getString("pr_in_day"));
				map.put("prOutDay", rs.getString("pr_out_day"));
				map.put("prState", rs.getString("pr_state"));
				map.put("prOutTarget", rs.getString("pr_out_target"));
				map.put("prLocation", rs.getString("pr_location"));
				map.put("prCount", rs.getString("pr_count"));
				map.put("prNeeds", rs.getString("pr_needs"));
			}
		} catch (SQLException e) {
			System.out.println("selectProductUpdate SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return map;
	}
	
	@Override
	public Map<String, String> selectItemUpdate(String itId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from item where it_id = ?";
		Map<String, String> map = new HashMap<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, itId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				map.put("itId", itId);
				map.put("itName", rs.getString("it_name"));
				map.put("itCode", rs.getString("it_code"));
				map.put("itManufacturer", rs.getString("it_manufacturer"));
				map.put("itAcquisition", rs.getString("it_acquisition"));
				map.put("itPrice", rs.getString("it_price"));
				map.put("itContent", rs.getString("it_content"));
				map.put("itImage", rs.getString("it_image"));
				map.put("itPeriod", rs.getString("it_period"));
			}
		} catch (SQLException e) {
			System.out.println("selectItemUpdate SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return map;
	}
	
	@Override
	public int updateProductUpdate(Map<String, String>map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update product set pr_in_day = to_date(?, 'yyyy-mm-ddhh24:mi'), pr_state = ?, pr_count = ?, "
				+ "pr_out_day = to_date(?, 'yyyy-mm-ddhh24:mi'), pr_out_target = ?, pr_location = ?, pr_needs = ?  where pr_id = ?";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, map.get("tb_prInDay"));
			pstmt.setString(2, map.get("sb_prState"));
			pstmt.setString(3, map.get("tb_prCount"));
			pstmt.setString(4, map.get("tb_prOutDay"));
			pstmt.setString(5, map.get("tb_prOutTarget"));
			pstmt.setString(6, map.get("tb_prLocation"));
			pstmt.setString(7, map.get("ta_prNeeds"));
			pstmt.setString(8, map.get("tb_prId"));
			result = pstmt.executeUpdate();
		} catch (SQLException e){
			System.out.println("updateProductUpdate SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	@Override
	public int updateProductUpdateAll(Map<String, String>map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update item set it_name = ?, it_code = ?, it_manufacturer = ?, it_price = ?, it_period = ?, it_content = ?, it_acquisition = ?, it_image = ? where it_id = ?";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, map.get("tb_itName"));
			pstmt.setString(2, map.get("tb_itCode"));
			pstmt.setString(3, map.get("tb_itManufacturer"));
			pstmt.setString(4, map.get("tb_itPrice"));
			pstmt.setString(5, map.get("tb_itPeriod"));
			pstmt.setString(6, map.get("ta_itContent"));
			pstmt.setString(7, map.get("tb_itAcquisition"));
			pstmt.setString(8, map.get("tb_itImage"));
			pstmt.setString(9, map.get("hi_itId"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateProductUpdateAllIt SQL Error");
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	@Override
	public List<Map<String, String>> selectProductInsertRequest() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<>();
		String sql = "select pr_id, pr_first_day from product where pr_state='wa_ninput'";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("prId", rs.getString("pr_id"));
				map.put("prFDay", rs.getString("pr_first_day"));
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("selectProductInsertRequest SQL Error");
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	@Override
	public Map<String, String> selectProductInsertPr(String prId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from product where pr_id = ?";
		Map<String, String> map = new HashMap<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, prId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				map.put("prId", prId);
				map.put("itId", rs.getString("it_id"));
				map.put("prFirstDay", rs.getString("pr_first_day"));
				map.put("prInDay", rs.getString("pr_in_day"));
				map.put("prOutDay", rs.getString("pr_out_day"));
				map.put("prOutTarget", rs.getString("pr_out_target"));
				map.put("prLocation", rs.getString("pr_location"));
				map.put("prNeeds", rs.getString("pr_needs"));
				map.put("prState", rs.getString("pr_state"));
				map.put("prCount", rs.getString("pr_count"));
				map.put("prQr", rs.getString("pr_qr"));
			}
		} catch (SQLException e) {
			System.out.println("selectProductInsertPr SQL Error");
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return map;
	}
	
	@Override
	public Map<String, String> selectProductInsertIt(String itId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from item where it_id = ?";
		Map<String, String> map = new HashMap<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, itId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				map.put("itName", rs.getString("it_name"));
				map.put("itCode", rs.getString("it_code"));
				map.put("itPrice", rs.getString("it_price"));
				map.put("itAcquisition", rs.getString("it_acquisition"));
				map.put("itPeriod", rs.getString("it_period"));
				map.put("itManufacturer", rs.getString("it_manufacturer"));
				map.put("itContent", rs.getString("it_content"));
				map.put("itImage", rs.getString("it_image"));
			}
		} catch (SQLException e) {
			System.out.println("selectProductInsertIt SQL Error");
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return map;
	}
	
	@Override
	public int updateProductInsertPr(Map<String, String> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update product set it_id =? , it_name = ?, pr_first_day = to_date(sysdate, 'yyyy-mm-ddhh24:mi'), pr_in_day = to_date(?, 'yyyy-mm-ddhh24:mi'), "
				+ "pr_state = ?, pr_count = ? where pr_id = ?";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, map.get("tb_itId"));
			pstmt.setString(2, map.get("tb_itName"));
			pstmt.setString(3, map.get("tb_prInDay"));
			pstmt.setString(4, map.get("sb_prState"));
			pstmt.setString(5, map.get("tb_prCount"));
			pstmt.setString(6, map.get("tb_prId"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateProductInsertPr SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	@Override
	public int updateProductInsertIt(Map<String, String> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from item where it_id = ?";
		String sql2 = "update item set it_name=?, it_code=?, it_price=?, it_acquisition=?, it_period=?, it_manufacturer=?, it_content=?, it_image=? where it_id=?";
		String sql3 = "insert into item values(?, ?, ?, ?, ?, ?, ?, ?, ? )";
		String sql4 = "select 'it'||trim(to_char(item_seq.nextval,'0000000000')) AS \"num\" from dual";
		int result = 0;
		int kuku = 0;
		String itId = null;
		try {
			con = factory.getConnection();
			if (map.get("tb_itId").trim().length() > 2) {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, map.get("tb_itId"));
				rs = pstmt.executeQuery();
				if(rs.next()) {  
					itId = rs.getString("it_id");
				}
			}else { 
				pstmt = con.prepareStatement(sql4);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					itId = rs.getString("num");
					kuku=1;
				}
			}
			if(kuku != 1) {
				pstmt = con.prepareStatement(sql2);
				pstmt.setString(1, map.get("tb_itName"));
				pstmt.setString(2, map.get("tb_itCode"));
				pstmt.setInt(3, Integer.parseInt(map.get("tb_itPrice")));
				pstmt.setInt(4, Integer.parseInt(map.get("tb_itAcquisition")));
				pstmt.setInt(5, Integer.parseInt(map.get("tb_itPeriod")));
				pstmt.setString(6, map.get("tb_itManufacturer"));
				pstmt.setString(7, map.get("tb_itContent"));
				pstmt.setString(8, map.get("tb_itImage"));
				pstmt.setString(9, map.get("tb_itId"));
				kuku = 2;
			} else {
				pstmt = con.prepareStatement(sql3);
				pstmt.setString(1, itId);
				pstmt.setString(2, map.get("tb_itName"));
				pstmt.setString(3, map.get("tb_itCode"));
				pstmt.setInt(4, Integer.parseInt(map.get("tb_itPrice")));
				pstmt.setInt(5, Integer.parseInt(map.get("tb_itAcquisition")));
				pstmt.setInt(6, Integer.parseInt(map.get("tb_itPeriod")));
				pstmt.setString(7, map.get("tb_itManufacturer"));
				pstmt.setString(8, map.get("tb_itContent"));
				pstmt.setString(9, map.get("tb_itImage"));
				if (pstmt.executeUpdate() == 1) {
					System.out.println("자산자산자산갱신");
					String sql5 = "update product set it_id =? where pr_id = ?";
					pstmt = con.prepareStatement(sql5);
					pstmt.setString(1, itId);
					pstmt.setString(2, map.get("tb_prId"));
					kuku=3;
				} else {
					return 0;
				}
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateProductInsertIt SQL Error");
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	/* ======================================== by 김재림 ================================================= */
	@Override
	public int updateAssetState(String assetState, String productId) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql ="update product set pr_state = ? where pr_id = ?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, assetState);
			pstmt.setString(2, productId);
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}

	@Override
	public List<Map<String, String>> selectAssetList(String assetState, String itemId) {
		List<Map<String, String>> returnValue = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.*, to_char(b.IT_ACQUISITION, '999,999,999,990') price from product a, item b where a.it_id = b.it_id and pr_state = ? and a.it_id = ?";
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, assetState);
			pstmt.setString(2, itemId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String, String> item = new HashMap<>();
				item.put("id", rs.getString("pr_id"));
				item.put("name", rs.getString("it_name"));
				item.put("entrydate", rs.getString("pr_in_day"));
				item.put("recentdate", rs.getString("pr_out_day"));
				item.put("unstorecount", rs.getString("pr_count"));
				item.put("price", rs.getString("price"));
				returnValue.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return returnValue;
	}

	@Override
	public List<Map<String, String>> selectRequestAssetList(String assetState) {
		List<Map<String, String>> returnValue = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select ou_id, pr_id, it_id, ou_count, to_char(ou_out_day, 'yyyy-MM-dd') req_day from output where ou_complete = 'N' and pr_id is not null and ou_state = ? order by req_day";
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, assetState);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String, String> item = new HashMap<>();
				item.put("requestId", rs.getString("ou_id"));
				item.put("productId", rs.getString("pr_id"));
				item.put("itemId", rs.getString("it_id"));
				item.put("assetState", assetState);
				item.put("amount", rs.getString("ou_count"));
				item.put("requestDay", rs.getString("req_day"));
				returnValue.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return returnValue;
	}

	@Override
	public List<Map<String, String>> selectRequestSearchAssetList(String assetState, String keyword) {
		/*List<Map<String, String>> returnValue = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = 
				"select b.자산id id, b.품목이름 name, b.구매일 entrydate, b.상태변경일 recentdate, b.출고횟수 unstorecount, b.가격 price "
				+ "from ("
					+ "select 자산 id, 자산||' '||품목이름||' '||구매일||' '||상태변경일||' '||출고횟수||' '||가격 search_field "
					+ "from 테이블 where 자산상태 = ?"
				+ ") a, 테이블 b "
				+ "where a.search_field regex_like(?) and a.id = b.id order by recentdate";
		System.out.println("sql = "+sql);
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, assetState);
			pstmt.setString(2, itemId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String, String> item = new HashMap<>();
				item.put("id", rs.getString("id"));
				item.put("name", rs.getString("name"));
				item.put("entrydate", rs.getString("entrydate"));
				item.put("recentdate", rs.getString("recentdate"));
				item.put("unstorecount", rs.getString("unstorecount"));
				item.put("price", rs.getString("price"));
				returnValue.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}*/
		return null;
	}

	@Override
	public int updateAssetRentalOut(Map<String, String> info) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql1 = "update product set pr_state = ? where pr_id = ?";
		String sql2 = "update output set pr_id = ? where ou_id = ?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql1);
			pstmt.setString(1, "re_output");
			pstmt.setString(2, info.get("id"));
			int result = pstmt.executeUpdate();
			
			if (result != 1) {
				return 0;
			}
			
			pstmt.clearParameters();
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, info.get("id"));
			pstmt.setString(2, info.get("requestId"));
			returnValue = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}

	@Override
	public int updateUnstore(Map<String, String> info) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql1 = "update product set pr_state = ? where pr_id = ?";
		String sql2 = "update output set ou_complete = 'Y', ou_day=SYSTIMESTAMP where ou_id = ?";
		String assetState = null;
		switch (info.get("assetState")) {
		case "re_output":
			assetState = "do_product";
			break;
		case "re_exoutput":
			assetState = "do_exoutput";
			break;
		case "re_disuse":
			assetState = "do_disuse";
		}
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql1);
			pstmt.setString(1, assetState);
			pstmt.setString(2, info.get("productId"));
			int result = pstmt.executeUpdate();
			if(result == 0) {
				return 0;
			}
			pstmt.clearParameters();
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, info.get("requestId"));
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}

	
	/* ======================================== by 이민정 ================================================= */

	@Override
	public List<Map<String, String>> selectInputRequest(String assetState){ 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select IN_ID, IT_ID, to_char(IN_COUNT, '9,990') in_count, IN_STATE, to_char(IN_DAY,'yyyy-MM-dd hh24:mi') in_day, IN_DELIVERY from input where IN_STATE = ? order by in_day, IN_ID";
		List<Map<String, String>> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, assetState);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String,String> map = new HashMap<>();
				map.put("id", rs.getString("IN_ID"));
				map.put("name", rs.getString("IT_ID"));
				map.put("count", rs.getString("IN_COUNT"));
				map.put("state", rs.getString("IN_STATE"));
				map.put("date", rs.getString("IN_DAY"));
				map.put("delivery", rs.getString("IN_DELIVERY"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	@Override
	public int insertProduct() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String prId = "";
		String sql1 = "SELECT to_char(SEQ_PR_ID.currval,'0000000000') AS \"pr_id\" FROM dual";
		String sql2 = "INSERT INTO product (pr_id, pr_first_day, pr_state, pr_count) VALUES (?,systimestamp,'wa_ninput',1)";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				prId = rs.getString("pr_id");
				//pstmt.close();
				pstmt = con.prepareStatement(sql2);
				pstmt.setString(1, "PR"+prId);
				return pstmt.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt,rs);
		}
		return 0;
	}
	
	@Override
	public int updateProduct(String inputId, String assetState) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String prId = "";
		String sql1 = "SELECT pr_id FROM product WHERE in_id=?";
		String sql2 = "UPDATE product SET pr_state=? WHERE pr_id=?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql1);
			pstmt.setString(1, inputId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				prId = rs.getString("pr_id");
				pstmt.close();
				pstmt = con.prepareStatement(sql2);
				pstmt.setString(1, assetState);
				pstmt.setString(2, prId);
				return pstmt.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return 0;
	}
	
	public int updateInputState(String inputId, String assetState){ 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		if(assetState.equals("re_ninput")) {
			sql = "update input set IN_STATE = 'wa_ninput', IN_FDAY = SYSTIMESTAMP, PR_ID = 'PR'||trim(to_char(SEQ_PR_ID.nextval,'0000000000')), IN_COMPLETE ='Y'  where IN_ID = ? ";
		}else if(assetState.equals("re_reinput")) {
			sql = "update input set IN_STATE = 'wa_check', IN_FDAY = SYSTIMESTAMP, IN_COMPLETE ='Y' where IN_ID = ?";
		}else if(assetState.equals("re_exinput")) {
			sql = "update input set IN_STATE = 'wa_policy', IN_FDAY = SYSTIMESTAMP, IN_COMPLETE ='Y' where IN_ID = ?";
		}
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, inputId);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				if(assetState.equals("re_ninput")) {
					//insertProduct();
					String prId = "";
					String sql11 = "SELECT trim(to_char(SEQ_PR_ID.currval,'0000000000')) AS \"pr_id\" FROM dual";
					String sql22 = "INSERT INTO product (pr_id, pr_first_day, pr_state, pr_count) VALUES (?,systimestamp,'wa_ninput',1)";
						pstmt = con.prepareStatement(sql11);
						rs = pstmt.executeQuery();
						if(rs.next()) {
							prId = rs.getString("pr_id");
							//pstmt.close();
							pstmt = con.prepareStatement(sql22);
							pstmt.setString(1, "PR"+prId);
							return pstmt.executeUpdate();
						}
				} else {
//					updateProduct(inputId, assetState);
					String prId = "";
		               String sql111 = "SELECT pr_id FROM product WHERE in_id=?";
		               String sql222 = "UPDATE product SET pr_state=? WHERE pr_id=?";
		               pstmt = con.prepareStatement(sql111);
		               pstmt.setString(1, inputId);
		               rs = pstmt.executeQuery();
		               if(rs.next()) {
		                  prId = rs.getString("pr_id");
		                  pstmt.close();
		                  pstmt = con.prepareStatement(sql222);
		                  pstmt.setString(1, assetState);
		                  pstmt.setString(2, prId);
		                  return pstmt.executeUpdate();
		               }
				}
			}
			//return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return 0;
	}
	
	
	   public List<Map<String, String>> selectInputRequest(String assetState, String productName){ 
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = "select i.in_id, i.it_id, i.in_state, i.in_count, to_char(i.in_day, 'yyyy-MM-dd') IN_DAY, i.in_delivery from (select in_id, in_id||' '||it_id||' '||in_count||' '||in_day||' '||in_delivery||' '||in_state combination from input) c, input i where c.in_id = i.in_id and i.in_state = ? and regexp_like(c.combination, ?)";
	      List<Map<String, String>> list = new ArrayList<>();
	      
	      try {
	         con = factory.getConnection();
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, assetState);
	         pstmt.setString(2, productName);
	         rs = pstmt.executeQuery();
	         while(rs.next()) {
	            Map<String, String> map = new HashMap<>();
	            map.put("id", rs.getString("IN_ID"));
	            map.put("name", rs.getString("IT_ID"));
	            map.put("count", rs.getString("IN_COUNT"));
	            map.put("state", rs.getString("IN_STATE"));
	            map.put("date", rs.getString("IN_DAY"));
	            map.put("delivery", rs.getString("IN_DELIVERY"));
	            list.add(map);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }finally {
	         factory.close(con, pstmt, rs);
	      }
	      return list;
	   }
	
	public List<Map<String, String>> selectInputList(String assetState){ 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="select IN_ID, IT_ID, to_char(IN_COUNT, '9,990') in_count, IN_STATE, to_char(IN_FDAY,'yyyy-MM-dd hh24:mi') in_fday, IN_DELIVERY from input where IN_STATE =?";
		List<Map<String, String>> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, assetState);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("id", rs.getString("IN_ID"));
				map.put("name", rs.getString("IT_ID"));
				map.put("count", rs.getString("IN_COUNT"));
				map.put("state", rs.getString("IN_STATE"));
				map.put("date", rs.getString("IN_FDAY"));
				map.put("delivery", rs.getString("IN_DELIVERY"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	public List<Map<String, String>> selectInputSearch(String assetState, String productName){ 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select i.in_id, i.it_id, i.in_count, to_char(i.in_day, 'yyyy-MM-dd hh24:mi') IN_DAY, i.in_delivery from (select in_id, in_id||' '||it_id||' '||in_count||' '||in_day||' '||in_delivery combination from input) c, input i where c.in_id = i.in_id and i.in_state = ? and regexp_like(c.combination, ?)";
		List<Map<String, String>> list = new ArrayList<>();
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, assetState);
			pstmt.setString(2, productName);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("id", rs.getString("IN_ID"));
				map.put("name", rs.getString("IT_ID"));
				map.put("count", rs.getString("IN_COUNT"));
				map.put("date", rs.getString("IN_DAY"));
				map.put("delivery", rs.getString("IN_DELIVERY"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	@Override
	public ArrayList<Product> selectRepairList(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT PR_ID, BUY_ID, IT_ID, IT_NAME, PR_STATE, to_char(PR_FIRST_DAY,'yyyy-MM-dd hh24:mi') pr_first_day,"
				+ "to_char(PR_IN_DAY,'yyyy-MM-dd hh24:mi') pr_in_day, to_char(PR_OUT_DAY,'yyyy-MM-dd hh24:mi') pr_out_day,"
				+ "PR_LOCATION, PR_NEEDS, PR_OUT_TARGET ,PR_COUNT, PR_QR FROM PRODUCT WHERE PR_STATE ='wa_check' or PR_STATE='wa_repair' ORDER BY PR_IN_DAY ";
		ArrayList<Product> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String id = null;
			String itName = null;
			String inDay = null;
			String state = null;
			String buyId = null;
			String itId = null;
			String firstDay = null;
			String outDay = null;
			String outTarget = null;
			String location = null;
			String needs = null;
			int count = 0;
			String qr = null;
			while(rs.next()) {
				id = rs.getString("PR_ID");
				itName = rs.getString("IT_NAME");
				inDay = rs.getString("PR_IN_DAY");
				state = rs.getString("PR_STATE");
				buyId =  rs.getString("BUY_ID");
				itId =  rs.getString("IT_ID");
				firstDay =  rs.getString("PR_FIRST_DAY");
				outDay =  rs.getString("PR_OUT_DAY");
				outTarget =  rs.getString("PR_OUT_TARGET");
				location =  rs.getString("PR_LOCATION");
				needs =  rs.getString("PR_NEEDS");
				count =  rs.getInt("PR_COUNT");
				qr =  rs.getString("PR_QR");
				list.add(new Product( id,  buyId,  itId,  itName,  firstDay,  inDay,  outDay,
						 outTarget,  location,  needs,  state,  count,  qr));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	@Override
	public List<Map<String, String>> selectRepairList(String keyword, String selectName) { 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<Map<String, String>> list = new ArrayList<>();
		if(selectName.equals("자산ID")) {
			sql = "SELECT PR_ID, IT_ID, IT_NAME, PR_STATE, to_char(PR_IN_DAY,'yyyy-MM-dd hh24:mi') pr_in_day FROM PRODUCT WHERE PR_ID like ? and (PR_STATE='wa_check' or PR_STATE='wa_repair')";
		}else if(selectName.equals("품목명"))
			sql = "SELECT PR_ID, IT_ID, IT_NAME, PR_STATE, to_char(PR_IN_DAY,'yyyy-MM-dd hh24:mi') pr_in_day FROM PRODUCT WHERE IT_NAME like ? and (PR_STATE='wa_check' or PR_STATE='wa_repair')";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("id", rs.getString("PR_ID"));
				map.put("itemId", rs.getString("IT_ID"));
				map.put("itemName", rs.getString("IT_NAME"));
				map.put("state", rs.getString("PR_STATE"));
				map.put("date", rs.getString("PR_IN_DAY"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	
	@Override
	public Map<String, String> selectRepairForm(String productId, String productState) {
		Map<String, String> returnValue = new HashMap<>();
		StringBuilder tempSql = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String waitCheckSql = "select PR_ID, IT_ID, IT_NAME, to_char(sysdate,'yyyy-MM-dd hh24:mi') today from product where pr_id = ? ";//점검대기
		String waitRepairSql ="select b.IT_ID, a.IT_NAME, a.PR_ID, to_char(sysdate,'yyyy-MM-dd hh24:mi') today, a.RE_CONTENT from repair a, product b where b.pr_id = ? and b.pr_id = a.pr_id and RE_STATE = ?";//수리대기
		try {
			con = factory.getConnection();
			switch (productState) {
			case "wa_check":
				pstmt = con.prepareStatement(waitCheckSql);
				pstmt.setString(1, productId);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					returnValue.put("productId", rs.getString("PR_ID"));
					returnValue.put("itemId", rs.getString("IT_ID"));
					returnValue.put("itemName", rs.getString("IT_NAME"));
					returnValue.put("todayDate", rs.getString("today"));
				}
				break;
			case "wa_repair":
				pstmt = con.prepareStatement(waitRepairSql);
				pstmt.setString(1, productId);
				pstmt.setString(2, productState);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					returnValue.put("productId", rs.getString("PR_ID"));
					returnValue.put("itemId", rs.getString("IT_ID"));
					returnValue.put("itemName", rs.getString("IT_NAME"));
					returnValue.put("todayDate", rs.getString("today"));
					returnValue.put("repairContent", rs.getString("RE_CONTENT"));
				}
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return returnValue;
	}
	

	@Override
	public List<Map<String, String>> selectRepairPartsList(String itemId) { 
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql ="select PA_ID, PA_SEARCH, PA_TOTAL from parts where it_id=?"; 
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, itemId);
			rs = pstmt.executeQuery();
			while(rs.next()) { 
				Map<String,String> map = new HashMap<>();
				map.put("partId", rs.getString("PA_ID"));
				map.put("partName", rs.getString("PA_SEARCH"));
				map.put("partCount", ""+rs.getInt("PA_TOTAL"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	

	  @Override
	   public int insertRepairResult(Map<String, Object> formInput, Map<String, String> partsInput) {
	      int returnValue = -1;
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      RempUtility ru = new RempUtility();
	      StringBuilder temp = new StringBuilder();
	      String repairSort = null;
	      
	      String selectSql ="select SEQ_RE_ID.nextval seq from dual";
	      String insertSql ="insert into repair values(?,?,?,?,?,?,?,SYSTIMESTAMP,?)";
	      String insertPartSql = "insert into use_parts values(?,?,?)";
	      String updatePartSql = "update parts set PA_TOTAL = PA_TOTAL - to_number(?) where PA_ID = ?";
	      String insertOutputsql = "INSERT INTO output (ou_id, pr_id, it_id, ou_state, ou_out_day, ou_count, ou_complete) VALUES ('ou'||trim(to_char(output_seq.nextval,'0000000000')),?, (select it_id from product where pr_id=?),?,systimestamp,?,?)";
	      String updateProductSql ="update product set PR_STATE=? where PR_ID =?";
	      
	      switch (formInput.get("repairSort").toString()) {
	      case "wa_product":
	         repairSort = "내부수리완료";
	         break;
	      case "wa_repair":
	         repairSort = "수리대기";
	         break;
	      case "re_exoutput":
	         repairSort = "외부수리";
	         break;
	      case "re_disuse":
	         repairSort = "수리불가능";
	      }
	      try {
	         //시퀸스 얻어오기
	         con = factory.getConnection();
	         pstmt = con.prepareStatement(selectSql);
	         rs = pstmt.executeQuery();
	         String repairId = null;
	         if(rs.next()) {
	            repairId = ru.getIdString("RE", rs.getInt("seq"), 12);
	         } else {
	            return -1;
	         }
	         returnValue += 2;
	         //점검결과 등록
	         System.out.println(formInput.get("repairSort").toString());
	         pstmt.clearParameters();
	         pstmt = con.prepareStatement(insertSql);
	         pstmt.setString(1, repairId);
	         pstmt.setString(2, formInput.get("itName").toString());
	         pstmt.setString(3, formInput.get("productId").toString());
	         pstmt.setString(4, formInput.get("engineerId").toString());
	         pstmt.setString(5, formInput.get("engineerName").toString());
	         pstmt.setString(6, repairSort);
	         pstmt.setString(7, formInput.get("repairSort").toString());
	         pstmt.setString(8, formInput.get("repairContent").toString());
	         int insertResult = pstmt.executeUpdate();
	         if (insertResult == 0) {
	            return -1;
	         }
	         returnValue += 1;
	         //자산테이블 변경
	         pstmt.clearParameters();
	         pstmt = con.prepareStatement(updateProductSql);
	         pstmt.setString(1, formInput.get("repairSort").toString());
	         pstmt.setString(2, formInput.get("productId").toString());
	         int updateProductResult = pstmt.executeUpdate();
	         if(updateProductResult == 0) {
	            return -1;
	         }
	         returnValue += 1;
	         
	         if(repairSort.equals("외부수리")) {
	         //출고 등록
	         pstmt.clearParameters();
	         pstmt = con.prepareStatement(insertOutputsql);
	         pstmt.setString(1, formInput.get("productId").toString());
	         pstmt.setString(2, formInput.get("productId").toString());
	           pstmt.setString(3, "re_exoutput");
	           pstmt.setInt(4, Integer.parseInt("1"));
	           pstmt.setString(5, "N");
	           int insertOutputResult = pstmt.executeUpdate();
	           returnValue += 1;
	         }
	         if(repairSort.equals("내부수리완료")){
	            //사용부품 등록
	            if(partsInput != null) {
	            pstmt.clearParameters();
	            pstmt = con.prepareStatement(insertPartSql);
	            Set<String> keys = partsInput.keySet();
	            Iterator<String> iter = keys.iterator();
	            while(iter.hasNext()) {
	               StringBuilder temp2 = new StringBuilder(iter.next());
	               pstmt.setString(1, repairId);
	               pstmt.setString(2, temp2.toString());
	               pstmt.setString(3, partsInput.get(temp2.toString()));
	               pstmt.addBatch();
	            }
	            int[] partsInsertResult = pstmt.executeBatch();
	            returnValue += 1;
	            
	            //부품테이블 수량 업데이트
	            pstmt.clearParameters();
	            pstmt = con.prepareStatement(updatePartSql);
	            Set<String> keys2 = partsInput.keySet();
	            Iterator<String> iter2 = keys2.iterator();
	            while(iter2.hasNext()) {
	               StringBuilder temp2 = new StringBuilder(iter2.next());
	               pstmt.setString(1, partsInput.get(temp2.toString()));
	               pstmt.setString(2, temp2.toString());
	               pstmt.addBatch();
	            }
	            int[] partsUpdateResult = pstmt.executeBatch();
	            returnValue += 1;
	            }
	         }

	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         factory.close(con, pstmt, rs);
	      }
	      if (returnValue < 3 || returnValue > 6) {
	         returnValue = -1;
	      }
	      return returnValue;
	   }
	

	@Override
	public int updateProductState(String productId, String repairSort) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String state = "";
		if(repairSort.equals("wa_product")) {
			state ="wa_product";
		}else if(repairSort.equals("wa_repair")) {
			state ="wa_repair";
		}else if(repairSort.equals("re_exoutput")) {
			state ="re_exoutput";
		}else if(repairSort.equals("re_disuse")) {
			state ="re_disuse";
		}
		String sql="update product set PR_STATE=? where PR_ID =?";
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, state);
			pstmt.setString(2, productId);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				factory.close(con, pstmt, null);
		}
		return 0;
	}
	

	@Override
	public List<Map<String, String>> selectAllParts() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "select PA_ID, PA_MODEL, PA_SEARCH, IT_ID, PA_MANUFACTURER, PA_TOTAL, PA_SAFETY from parts order by PA_ID";
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("partId", rs.getString("PA_ID"));
				map.put("partModel", rs.getString("PA_MODEL"));
				map.put("partSearch", rs.getString("PA_SEARCH"));
				map.put("itemId", rs.getString("IT_ID"));
				map.put("partManufacturer", rs.getString("PA_MANUFACTURER"));
				map.put("partTotal", rs.getString("PA_TOTAL"));
				map.put("partSafety", rs.getString("PA_SAFETY"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				factory.close(con, pstmt, rs);
		}
		return list;
	}
	

	@Override
	public List<Map<String,String>> selectAllRepairResult(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "select RE_ID, IT_NAME, PR_ID, RE_ENGINEER_ID, RE_ENGINEER_NAME, RE_SORT, RE_STATE, to_char(RE_DAY,'yyyy-MM-dd hh24:mi') re_day, RE_CONTENT from repair where RE_ENGINEER_ID = ? order by RE_ID";
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<>();
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("repairId", rs.getString("RE_ID"));
				map.put("itemName", rs.getString("IT_NAME"));
				map.put("productId", rs.getString("PR_ID"));
				map.put("engineerId", rs.getString("RE_ENGINEER_ID"));
				map.put("engineerName", rs.getString("RE_ENGINEER_NAME"));
				map.put("repairSort", rs.getString("RE_SORT"));
				map.put("repairState", rs.getString("RE_STATE"));
				map.put("repairDate", rs.getString("re_day"));
				map.put("repairContent", rs.getString("RE_CONTENT"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	

	@Override
	public int insertCustomerBuy(String loginId, Map<String,String> rentalInfo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		if(rentalInfo.get("rb_payment").equals("account")) {//계좌이체
			sql = "insert into buy (BUY_ID, BUY_DAY, CU_ID, IT_ID, BUY_PRICE, BUY_START, BUY_END, BUY_POST, BUY_ADDR, BUY_ADD_D, BUY_METHOD, BUY_CBANK, BUY_TRANSFER,BUY_PAY) VALUES('BU'||trim(to_char(SEQ_BU_ID.nextval,'0000000000')),SYSTIMESTAMP,?,?,?,?,?,?,?,?,?,?,?,?)";
		}else if(rentalInfo.get("rb_payment").equals("card")) {//카드결제
			sql = "insert into buy (BUY_ID, BUY_DAY, CU_ID, IT_ID, BUY_PRICE, BUY_START, BUY_END, BUY_POST, BUY_ADDR, BUY_ADD_D, BUY_METHOD, BUY_CCOMPANY, BUY_CARD,BUY_PAY) VALUES('BU'||trim(to_char(SEQ_BU_ID.nextval,'0000000000')),SYSTIMESTAMP,?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);//
			pstmt.setString(2, rentalInfo.get("itemId"));
			pstmt.setInt(3, Integer.parseInt(rentalInfo.get("price")));
			pstmt.setString(4, rentalInfo.get("startDate"));
			pstmt.setString(5, rentalInfo.get("endDate"));
			pstmt.setString(6, rentalInfo.get("tb_post"));
			pstmt.setString(7, rentalInfo.get("tb_addr"));
			pstmt.setString(8, rentalInfo.get("tb_addD"));
			pstmt.setString(9, rentalInfo.get("rb_payment"));
			if(rentalInfo.get("rb_payment").equals("card")) {
				pstmt.setString(10, rentalInfo.get("sb_card"));
				pstmt.setString(11, rentalInfo.get("cardNum"));
			}else if(rentalInfo.get("rb_payment").equals("account")) {
				pstmt.setString(10, rentalInfo.get("sb_bank"));
				pstmt.setString(11, rentalInfo.get("accountNum"));
			}
			pstmt.setString(12, "매월납부");//하드코딩 
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, null);
		}
		return 0;
	}
	

	@Override
	   public int insertCustomerBuyOutput(Map<String, String> adviceInfo) {
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      String sql = "INSERT INTO output (ou_id, it_id, ou_state, ou_out_day, ou_count, ou_complete) VALUES ('ou'||trim(to_char(output_seq.nextval,'0000000000')),?,?,systimestamp,?,?)";
	      try {
	         con = factory.getConnection();
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, adviceInfo.get("itemId"));
	         pstmt.setString(2, "re_output");
	         pstmt.setInt(3, Integer.parseInt(adviceInfo.get("tb_itNumber")));
	         pstmt.setString(4, "N");
	         return pstmt.executeUpdate();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
			factory.close(con, pstmt, null);
	      }
	      return 0;
	   }
	   

	@Override
	public List<Map<String, String>> selectPartsList(String type, String keyword){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<Map<String, String>> list = new ArrayList<>();
		if(type.equals("partId")) {
			sql = "select PA_ID, PA_MODEL, PA_SEARCH, IT_ID, PA_MANUFACTURER, PA_TOTAL, PA_SAFETY from parts where PA_ID like ?";
		}else if(type.equals("PartSearchName")) {
			sql = "select PA_ID, PA_MODEL, PA_SEARCH, IT_ID, PA_MANUFACTURER, PA_TOTAL, PA_SAFETY from parts where PA_SEARCH like ?";
		}else if(type.equals("ItemId")) {
			sql = "select PA_ID, PA_MODEL, PA_SEARCH, IT_ID, PA_MANUFACTURER, PA_TOTAL, PA_SAFETY from parts where IT_ID like ?";
		}else if(type.equals("partManufacturer")) {
			sql = "select PA_ID, PA_MODEL, PA_SEARCH, IT_ID, PA_MANUFACTURER, PA_TOTAL, PA_SAFETY from parts where PA_MANUFACTURER like ?";
		}else if(type.equals("all")) {
			sql = "select p.pa_id, p.pa_search, p.it_id, p.pa_manufacturer, p.pa_model, p.pa_total, p.pa_safety\r\n" + 
					"from (select pa_id, pa_id||' '||pa_search||' '||it_id||' '||pa_manufacturer||' '||pa_model combination from parts) c, parts p\r\n" + 
					"where c.pa_id = p.pa_id and c.combination like ?";
		}
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("partId", rs.getString("PA_ID"));
				map.put("partModel", rs.getString("PA_MODEL"));
				map.put("partSearch", rs.getString("PA_SEARCH"));
				map.put("itemId", rs.getString("IT_ID"));
				map.put("partManufacturer", rs.getString("PA_MANUFACTURER"));
				map.put("partTotal", rs.getString("PA_TOTAL"));
				map.put("partSafety", rs.getString("PA_SAFETY"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return list;
	}
	

	@Override
	   public List<Map<String, String>> selectRepairResult(String engineerId, String startDate, String endDate, String repairSort){
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      String sql ="";
	      ResultSet rs = null;
	      List<Map<String, String>> list = new ArrayList<>();
	      if(repairSort.equals("wa_repair")) {
	         sql = "select RE_ID, IT_NAME, PR_ID, RE_ENGINEER_ID, RE_ENGINEER_NAME, RE_SORT, RE_STATE, RE_DAY, RE_CONTENT from repair where to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') >= to_date(?,'yyyy/MM/dd') and to_date(to_char(RE_DAY,'yyyy/MM/dd'), 'yyyy/MM/dd') <= to_date(?,'yyyy/MM/dd') and RE_STATE ='wa_repair' and RE_ENGINEER_ID = ?";
	      }else if(repairSort.equals("wa_product")) {
	         sql = "select RE_ID, IT_NAME, PR_ID, RE_ENGINEER_ID, RE_ENGINEER_NAME, RE_SORT, RE_STATE, RE_DAY, RE_CONTENT from repair where to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') >= to_date(?,'yyyy/MM/dd') and to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') <= to_date(?,'yyyy/MM/dd') and RE_STATE ='wa_product' and RE_ENGINEER_ID = ?";
	      }else if(repairSort.equals("re_exoutput")) {
	         sql = "select RE_ID, IT_NAME, PR_ID, RE_ENGINEER_ID, RE_ENGINEER_NAME, RE_SORT, RE_STATE, RE_DAY, RE_CONTENT from repair where to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') >= to_date(?,'yyyy/MM/dd') and to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') <= to_date(?,'yyyy/MM/dd') and RE_STATE ='re_exoutput' and RE_ENGINEER_ID = ?"; 
	      }else if(repairSort.equals("re_disuse")) {
	         sql = "select RE_ID, IT_NAME, PR_ID, RE_ENGINEER_ID, RE_ENGINEER_NAME, RE_SORT, RE_STATE, RE_DAY, RE_CONTENT from repair where to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') >= to_date(?,'yyyy/MM/dd') and to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') <= to_date(?,'yyyy/MM/dd') and RE_STATE ='re_disuse' and RE_ENGINEER_ID = ?";
	      }else if(repairSort.equals("all")) {
	         sql = "select RE_ID, IT_NAME, PR_ID, RE_ENGINEER_ID, RE_ENGINEER_NAME, RE_SORT, RE_STATE, RE_DAY, RE_CONTENT from repair where to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') >= to_date(?,'yyyy/MM/dd') and to_date(to_char(RE_DAY,'yyyy/MM/dd'),'yyyy/MM/dd') <= to_date(?,'yyyy/MM/dd') and RE_ENGINEER_ID = ?";
	      }
	      try {
	         con = factory.getConnection();
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, startDate);
	         pstmt.setString(2, endDate);
	         pstmt.setString(3, engineerId);
	         rs = pstmt.executeQuery();
	         while(rs.next()) {
	            Map<String, String> map = new HashMap<>();
	            map.put("repairId", rs.getString("RE_ID"));
	            map.put("itemName", rs.getString("IT_NAME"));
	            map.put("productId", rs.getString("PR_ID"));
	            map.put("engineerId", rs.getString("RE_ENGINEER_ID"));
	            map.put("engineerName", rs.getString("RE_ENGINEER_NAME"));
	            map.put("repairSort", rs.getString("RE_SORT"));
	            map.put("repairState", rs.getString("RE_STATE"));
	            map.put("repairDate", rs.getString("RE_DAY"));
	            map.put("repairContent", rs.getString("RE_CONTENT"));
	            list.add(map);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }finally {
	         factory.close(con, pstmt, rs);
	      }
	      return list;
	   }

	
	/* ======================================== by 이원호 ================================================= */
	
	@Override
	public int updateBuyEnd(String buyId, String date) { // 회수 시 시간변경
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql="update buy set buy_end=? where buy_id = ?";
		int result = 0;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date.trim().substring(0, 10));
			pstmt.setString(2, buyId.trim());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return result;
	}
	
	@Override
	public Map<String, String> selectCount(String itId, String itName) {
		Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = "SELECT it_id, it_name, it_period, it_price FROM item WHERE it_id=?";
	      String sql1 = "SELECT it_id, sum(pr_count) AS \"Wait\", pr_state FROM product WHERE it_id=? AND pr_state='wa_product' GROUP BY it_id, pr_state";
	      String sql2 = "SELECT it_id, sum(pr_count) AS \"Do\", pr_state FROM product WHERE it_id=? AND pr_state='do_product' GROUP BY it_id, pr_state";
	      String sql3 = "SELECT it_id, sum(pr_count) AS \"Refund\", pr_state FROM product WHERE it_id=? AND pr_state='re_return' GROUP BY it_id, pr_state";
	      Map<String, String> map = new HashMap<>();
	      try {
	         con = factory.getConnection();
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, itId.trim());
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	        	 map.put("itName", rs.getString("it_name"));
	        	 map.put("itPeriod", rs.getString("it_period"));
	        	 map.put("itPrice", rs.getString("it_price"));
	        	 
	        	 pstmt = con.prepareStatement(sql1);
		         pstmt.setString(1, itId.trim());
		         rs = pstmt.executeQuery();
		         if(rs.next()) {
		        	 map.put("waitCount", rs.getInt("Wait")+"");
		         } else {
		        	 map.put("waitCount", "0");
		         }
		         pstmt = con.prepareStatement(sql2);
		         pstmt.setString(1, itId.trim());
		         rs = pstmt.executeQuery();
		         if(rs.next()) {
		        	 map.put("doCount", rs.getInt("Do")+"");
			     } else {
			    	 map.put("doCount", "0");
			     }
		         pstmt = con.prepareStatement(sql3);
		         pstmt.setString(1, itId.trim());
		         rs = pstmt.executeQuery();
		         if(rs.next()) {
		        	 map.put("refundCount", rs.getInt("Refund")+"");
			     } else {
			    	 map.put("refundCount", "0");
			     }
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	         System.out.println("selectOutderId SQL Error");
	      }finally {
	         factory.close(con, pstmt, rs);
	      }
		return map;
	}

}
