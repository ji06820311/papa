package com.itgroup;

import com.itgroup.bean.Movie;   //  Movie import
import com.itgroup.dao.MovieDao; //  DAO import

import java.util.List;           //  List importsp
import java.util.Scanner;        //  Scanner import

public class MovieMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); //  변수명 scan으로 통일
        MovieDao dao = new MovieDao();

        while (true) {
            System.out.println("==== 영화 메뉴 ====");
            System.out.println("0:종료, 1:전체, 2:등록, 3:수정, 4:삭제, 5:1건조회, 6:장르검색, 7:평점범위, 8:개수");

            int menu = scan.nextInt();
            scan.nextLine(); // 개행 제거

            if (menu == 0) { System.out.println("종료합니다."); break; }

            switch (menu) {
                case 1: {
                    List<Movie> list = dao.selectAll();
                    if (list.isEmpty()) { System.out.println("데이터가 없습니다."); break; }
                    System.out.println("ID\t제목\t장르\t감독\t연도\t러닝\t평점\t가격\t재고");
                    for (Movie m : list) System.out.println(m.toString());
                    break;
                }
                case 2: {
                    System.out.println("[영화 등록]");
                    System.out.print("ID: "); int id = Integer.parseInt(scan.nextLine());
                    System.out.print("제목: "); String title = scan.nextLine();
                    System.out.print("장르: "); String genre = scan.nextLine();
                    System.out.print("감독: "); String director = scan.nextLine();
                    System.out.print("개봉연도: "); int year = Integer.parseInt(scan.nextLine());
                    System.out.print("러닝타임(분): "); int runtime = Integer.parseInt(scan.nextLine());
                    System.out.print("평점(0.0~10.0): "); double rating = Double.parseDouble(scan.nextLine());
                    System.out.print("가격: "); int price = Integer.parseInt(scan.nextLine());
                    System.out.print("재고: "); int stock = Integer.parseInt(scan.nextLine());

                    int r = dao.insert(new Movie(id, title, genre, director, year, runtime, rating, price, stock));
                    System.out.println(r == 1 ? "등록 성공" : (r == 0 ? "변경 없음" : "등록 오류"));
                    break;
                }
                case 3: {
                    System.out.println("[영화 수정]");
                    System.out.print("수정할 영화 ID: "); int id = Integer.parseInt(scan.nextLine());
                    Movie m = dao.getOne(id);
                    if (m == null) { System.out.println("해당 ID 없음"); break; }
                    System.out.println("현재: " + m);
                    System.out.print("제목: "); m.setTitle(scan.nextLine());
                    System.out.print("장르: "); m.setGenre(scan.nextLine());
                    System.out.print("감독: "); m.setDirector(scan.nextLine());
                    System.out.print("개봉연도: "); m.setYear(Integer.parseInt(scan.nextLine()));
                    System.out.print("러닝타임(분): "); m.setRuntime(Integer.parseInt(scan.nextLine()));
                    System.out.print("평점: "); m.setRating(Double.parseDouble(scan.nextLine()));
                    System.out.print("가격: "); m.setPrice(Integer.parseInt(scan.nextLine()));
                    System.out.print("재고: "); m.setStock(Integer.parseInt(scan.nextLine()));

                    int r = dao.update(m);
                    System.out.println(r == 1 ? "수정 성공" : (r == 0 ? "변경 없음" : "수정 오류"));
                    break;
                }
                case 4: {
                    System.out.println("[영화 삭제]");
                    System.out.print("삭제할 영화 ID: "); int id = Integer.parseInt(scan.nextLine());
                    int r = dao.delete(id);
                    if (r == -1) System.out.println("삭제 실패(오류)");
                    else if (r == 0) System.out.println("해당 ID 없음");
                    else System.out.println("삭제 완료");
                    break;
                }
                case 5: {
                    System.out.println("[영화 1건 조회]");
                    System.out.print("조회할 영화 ID: "); int id = Integer.parseInt(scan.nextLine());
                    Movie m = dao.getOne(id);
                    if (m == null) System.out.println("해당 ID 없음");
                    else {
                        System.out.println("ID\t제목\t장르\t감독\t연도\t러닝\t평점\t가격\t재고");
                        System.out.println(m.toString());
                    }
                    break;
                }
                case 6: {
                    System.out.println("[장르 검색]");
                    System.out.print("장르: "); String g = scan.nextLine();
                    List<Movie> list = dao.findByGenre(g);
                    if (list.isEmpty()) { System.out.println("데이터가 없습니다."); break; }
                    System.out.println("ID\t제목\t장르\t감독\t연도\t러닝\t평점\t가격\t재고");
                    for (Movie m : list) System.out.println(m.toString());
                    break;
                }
                case 7: {
                    System.out.println("[평점 범위 검색]");
                    System.out.print("최소 평점: "); double min = Double.parseDouble(scan.nextLine());
                    System.out.print("최대 평점: "); double max = Double.parseDouble(scan.nextLine());
                    List<Movie> list = dao.findByRatingRange(min, max);
                    if (list.isEmpty()) { System.out.println("데이터가 없습니다."); break; }
                    System.out.println("ID\t제목\t장르\t감독\t연도\t러닝\t평점\t가격\t재고");
                    for (Movie m : list) System.out.println(m.toString());
                    break;
                }
                case 8: {
                    int cnt = dao.getCount();
                    System.out.println("등록된 영화 수: " + cnt + "편");
                    break;
                }
                default:
                    System.out.println("없는 메뉴입니다.");
            }
        }
        scan.close();
    }
}
