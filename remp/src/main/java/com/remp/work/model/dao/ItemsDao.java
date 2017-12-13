package com.remp.work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.remp.work.model.dto.Item;

/**
 * @author 이민정
 *
 */
@Repository
public class ItemsDao {
	private FactoryDao factory;
	
	@Autowired
	public void setFactoryDao(FactoryDao factory) {
		this.factory = factory;
	}
	
	/**
	 * 고객렌탈 메인 화면 품목리스트 보여주기
	 * @return 고객렌탈 메인 화면 품목리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public ArrayList<Item> getItemList(){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql ="select * from item order by IT_NAME";
		ResultSet rs = null;
		ArrayList<Item> list = new ArrayList<Item>();
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String id = null;
			String name = null;
			String code = null;
			int price = 0;
			int acquisition = 0;
			String image = null;
			int period = 0;
			String content = null;
			String manufacturer = null;
			
			while(rs.next()) {
				id = rs.getString("IT_ID");
				name = rs.getString("IT_NAME");
				code = rs.getString("IT_CODE");
				price = rs.getInt("IT_PRICE");
				acquisition = rs.getInt("IT_ACQUISITION");
				image = rs.getString("IT_IMAGE");
				period = rs.getInt("IT_period");
				content = rs.getString("IT_CONTENT");
				manufacturer = rs.getString("IT_MANUFACTURER");
				list.add(new Item( id,  name,  code,  price,  acquisition,  period,  manufacturer,
						 content,  image));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	/**
	 * 제품 검색
	 * @param sb_search 검색카테고리
	 * @param item 검색어
	 * @return 제품검색 결과 리스트
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public ArrayList<Item> getSearchList(String sb_search, String item){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Item> list = new ArrayList<Item>();
		String sql= "";
		if(sb_search.equals("제품명")) {
			sql = "select * from item where IT_NAME like ? order by IT_NAME";
		}else if(sb_search.equals("제품코드")) {
			sql = "select * from item where IT_CODE like ? order by IT_NAME";
		}
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+item.trim()+"%");
			rs = pstmt.executeQuery();
			
			String id = null;
			String name = null;
			String code = null;
			int price = 0;
			int acquisition = 0;
			String image = null;
			int period = 0;
			String content = null;
			String manufacturer = null;
			
			while(rs.next()) {
				id = rs.getString("IT_ID");
				name = rs.getString("IT_NAME");
				code = rs.getString("IT_CODE");
				price = rs.getInt("IT_PRICE");
				acquisition = rs.getInt("IT_ACQUISITION");
				image = rs.getString("IT_IMAGE");
				period = rs.getInt("IT_period");
				content = rs.getString("IT_CONTENT");
				manufacturer = rs.getString("IT_MANUFACTURER");
				list.add(new Item( id,  name,  code,  price,  acquisition,  period,  manufacturer,
						 content,  image));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
	/**
	 * 렌탈 제품 상세보기
	 * @param itemId 품목아이디
	 * @return 품목dto
	 * @author 이민정
	 * @since ReMP v 1.0
	 */
	public Item getItem(String itemId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from item where IT_ID = ?";
		
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, itemId);
			rs = pstmt.executeQuery();
			
			String id = null;
			String name = null;
			String code = null;
			int price = 0;
			int acquisition = 0;
			String image = null;
			int period = 0;
			String content = null;
			String manufacturer = null;
			
			while(rs.next()) {
				id = rs.getString("IT_ID");
				name = rs.getString("IT_NAME");
				code = rs.getString("IT_CODE");
				price = rs.getInt("IT_PRICE");
				acquisition = rs.getInt("IT_ACQUISITION");
				image = rs.getString("IT_IMAGE");
				period = rs.getInt("IT_period");
				content = rs.getString("IT_CONTENT");
				manufacturer = rs.getString("IT_MANUFACTURER");
				return new Item( id,  name,  code,  price,  acquisition,  period,  manufacturer,
						 content,  image);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			factory.close(con, pstmt, rs);
		}
		return null;
	}
}
