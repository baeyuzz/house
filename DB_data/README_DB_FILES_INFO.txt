========================================
totaldump.dump 파일
- 사용되는 모든 테이블의 내용을 mysqldump 를 사용해 만든 파일

import 방법
mysql cmd 클라이언트에서(명령어를 통해 mysql 접속이 가능한 곳)
mysql 에 접속 후 아래 세 줄의 명령 실행
약 50MB 의 데이터로 모두 import 하는데 시간이 많이 소요될 수 있습니다.

mysql> create database if not exists ssafydb;
mysql> use ssafydb;
mysql> source /dump파일의경로/totaldump.dump;

========================================
아래 파일들은 각 테이블 또는 카테고리별로 나눠서
csv 파일 또는 mysqldump 파일로 내보낸 파일들
totaldump.dump 파일을 사용해 import 했다면
아래 내용은 무시해도 됩니다.
========================================
-- 외부에서 받아온 데이터
파일명(export 한 csv 파일): `[Table name].csv`

포함된 테이블
housedeal
houseinfo
envinfo
shopinfo
crime

========================================
-- 받아온 데이터가 아닌 테이블들
파일명(mysqldump 파일): `tables.sql`

포함된 테이블
ssafy_member
qna_board
reply
notice
interestregion
apt

========================================
-- select 박스용 지역코드 데이터

/* 주의: tables.sql 보다 용량이 큽니다
   dump 파일 import 기능을 사용하십시오
   성능이 좋은 컴퓨터라면 SQL 파일로 열어서 실행시켜도 됩니다. */
파일명(mysqldump 파일): `areacode.sql`
sidocode
guguncode
dongcode

