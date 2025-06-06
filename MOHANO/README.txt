run.bat -> window 실행
run.sh -> macOS, Linux 실행
mariadb는 설치되어있어야함-3308 포트로 서버 열고, java_db가 생성되어있어야함
메일 주소에 맞는 smtp 비밀번호 설정 필요

코드 수정사항 테스트 시 이클립스 내부 프로젝트에다가 lib 폴더 내의 라이브러리 추가
project 우클릭 -> properties -> build path -> configure.... -> add JARs(External X) mariadb 추가
MOHANO.jar 재생성 시 src 부분만 포함(lib는 포함 X)