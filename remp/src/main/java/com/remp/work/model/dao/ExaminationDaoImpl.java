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
 * 자산 실사를 위한 Dao
 * </p>
 * @author 김재림
 * @since JDK 1.8 이후
 * @version ReMP v 1.0
 */
@Repository("examinationDao")
public class ExaminationDaoImpl implements ExaminationDao{
	private static ExaminationDaoImpl instance;
	@Autowired
	private FactoryDao factory;

	private ExaminationDaoImpl() {
	}

	private ExaminationDaoImpl(FactoryDao factory) {
		this.factory = factory;
	}

	public static ExaminationDaoImpl getInstance() {
		return getInstance(null);
	}

	public static ExaminationDaoImpl getInstance(FactoryDao factory) {
		if (instance == null) {
			instance = new ExaminationDaoImpl(factory);
		}
		return instance;
	}

	@Override
	public int updateDueDiligencePlan(Map<String, String> info) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update EXAMINATION set EX_START = ?, EX_END = ?, EX_CONTENT = ? where EX_ID = ?";

		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.get("fromDate"));
			pstmt.setString(2, info.get("toDate"));
			pstmt.setString(3, info.get("misc"));
			pstmt.setString(4, info.get("planId"));
			
			System.out.println(factory);
			System.out.println(con);
			System.out.println(pstmt);
			System.out.println(sql);
			
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}

	@Override
	public List<Map<String, String>> selectDueDiligencePlanList(String keyword) {
		System.out.println(keyword);
		List<Map<String, String>> returnValue = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select b.ex_id, b.ex_start, b.ex_end, b.ex_content from (select ex_id, ex_id||' '||ex_start||' '||ex_end||' '||ex_content search from examination) a, examination b where regexp_like(search, ?) and a.ex_id = b.ex_id ORDER BY b.ex_start desc, b.ex_id";

		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			returnValue = new ArrayList<>();
			while (rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("planId", rs.getString(1));
				map.put("fromDate", rs.getString(2));
				map.put("toDate", rs.getString(3));
				map.put("misc", rs.getString(4));
				returnValue.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt, rs);
		}
		return returnValue;
	}

	@Override
	public int insertDueDiligencePlan(Map<String, String> jsonToMap) {
		int returnValue = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into examination(EX_ID, EX_START, EX_END, EX_CONTENT) values(?, ?, ?, ?)";

		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, jsonToMap.get("planId"));
			pstmt.setString(2, jsonToMap.get("fromDate"));
			pstmt.setString(3, jsonToMap.get("toDate"));
			pstmt.setString(4, jsonToMap.get("misc"));
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(con, pstmt);
		}
		return returnValue;
	}
}
