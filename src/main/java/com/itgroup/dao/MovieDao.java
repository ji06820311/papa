package com.itgroup.dao;

import com.itgroup.bean.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class MovieDao extends SuperDao {

    private Movie map(ResultSet rs) throws SQLException {
        Movie m = new Movie();
        m.setId(rs.getInt("id"));
        m.setTitle(rs.getString("title"));
        m.setGenre(rs.getString("genre"));
        m.setDirector(rs.getString("director"));
        m.setYear(rs.getInt("year"));
        m.setRuntime(rs.getInt("runtime"));
        m.setRating(rs.getDouble("rating"));
        m.setPrice(rs.getInt("price"));
        m.setStock(rs.getInt("stock"));
        return m;
    }

    public int insert(Movie m) {
        String sql = "INSERT INTO movies(id, title, genre, director, year, runtime, rating, price, stock) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getId());
            ps.setString(2, m.getTitle());
            ps.setString(3, m.getGenre());
            ps.setString(4, m.getDirector());
            ps.setInt(5, m.getYear());
            ps.setInt(6, m.getRuntime());
            ps.setDouble(7, m.getRating());
            ps.setInt(8, m.getPrice());
            ps.setInt(9, m.getStock());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return -1; }
    }

    public int update(Movie m) {
        String sql = "UPDATE movies SET title=?, genre=?, director=?, year=?, runtime=?, rating=?, price=?, stock=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getTitle());
            ps.setString(2, m.getGenre());
            ps.setString(3, m.getDirector());
            ps.setInt(4, m.getYear());
            ps.setInt(5, m.getRuntime());
            ps.setDouble(6, m.getRating());
            ps.setInt(7, m.getPrice());
            ps.setInt(8, m.getStock());
            ps.setInt(9, m.getId());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return -1; }
    }

    public int delete(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return -1; }
    }

    public Movie getOne(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Movie> selectAll() {
        String sql = "SELECT * FROM movies ORDER BY title ASC";
        List<Movie> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Movie> findByGenre(String genre) {
        String sql = "SELECT * FROM movies WHERE genre = ? ORDER BY title ASC";
        List<Movie> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, genre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Movie> findByRatingRange(double min, double max) {
        String sql = "SELECT * FROM movies WHERE rating BETWEEN ? AND ? ORDER BY rating DESC";
        List<Movie> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, min);
            ps.setDouble(2, max);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public int getCount() {
        String sql = "SELECT COUNT(*) AS cnt FROM movies";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt("cnt") : 0;
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}
