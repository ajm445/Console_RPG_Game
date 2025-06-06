# 🎮 Java Console RPG Game

> Java로 제작한 텍스트 기반 콘솔 RPG 게임입니다.  
> 직업을 선택하고 적과 전투하며, 상점에서 아이템을 구매하고 캐릭터를 성장시킬 수 있습니다.  
> 로그인 및 저장 기능을 통해 유저 정보를 파일로 관리할 수 있습니다.

---

## 🧩 주요 기능

- ✅ 사용자 회원가입 / 로그인 / 회원정보 수정 / 탈퇴
- ✅ 직업 선택: 전사, 도적, 마법사
- ✅ 적과 전투 (스테이지별 구성)
- ✅ 자동 전투 기능
- ✅ 골드 획득 및 소모
- ✅ 상점에서 아이템 구매 및 장착
- ✅ 아이템 능력치 반영 (공격력, 방어력)
- ✅ 캐릭터 상태 확인
- ✅ 게임 상태 파일 저장 및 불러오기

---

## 🗂️ 프로젝트 구조

```plaintext
📁 RPG_Console_Game
│
├── Main.java              # 게임 실행 시작 지점
├── GameManager.java       # 게임 메뉴 및 흐름 제어
├── Stage.java             # 스테이지 및 전투 진행
├── Enemy.java             # 적 정보 클래스
├── MyCharacter.java       # 캐릭터 클래스 (직업, 능력치 등)
├── User.java              # 사용자 정보 저장 클래스
├── UserManager.java       # 사용자 목록 관리 및 파일 저장/불러오기
├── Store.java             # 상점 UI 및 상호작용
├── StoreManager.java      # 상점 내부 로직 처리
├── Item.java              # 아이템 클래스
├── ItemManager.java       # 아이템 배열 및 관련 함수 관리
```

---

## ⚙️ 실행 방법
1. 이 저장소를 클론하거나 소스 파일을 다운로드합니다.
```aiignore
git clone https://github.com/yourname/console-rpg-java.git
cd console-rpg-java
```
2. Java 11 이상이 설치되어 있는지 확인합니다.
3. IDE에서 Main.java 파일을 실행하거나 터미널에서 아래 명령어로 컴파일 후 실행합니다.
```aiignore
javac *.java
java Main
```

---

## 👥 직업 설명
| 직업  | HP | 공격력   | 방어력 |
|-----|----|-------|------|
| 전사  | 높음 | 중간    | 높음|
| 도적  | 중간 | 높음    | 중간|
| 마법사 | 낮음 | 매우 높음 | 낮음 |

---

## 🛠️ 향후 추가 예정 기능 (미구현)
- 인벤토리 시스템
- 경험치 및 레벨업 시스템
- 직업별 고유 스킬
- 퀘스트 및 목표 시스템
- 스테이지 클리어 보상
- 저장 슬롯 기능

---

## 📂 데이터 저장
- 각 유저는 user_<id>.txt 형식으로 저장됩니다.
- 저장 정보: 아이디, 비밀번호, 보유 골드, 장착 아이템 인덱스, 구매 여부 등
- 유저 정보와 캐릭터 정보는 따로 저장됩니다.
- 유저의 캐릭터는 character_<id>.txt 형식으로 저장됩니다.

---

## 📢 제작자
- 자바 콘솔 기반 RPG 개인 프로젝트
- 개발 환경 : JDK 23(23.0.2) 버전, IntelliJ
- https://github.com/ajm445

---

## 📝 라이선스
본 프로젝트는 MIT 라이선스를 따릅니다. 자유롭게 사용하되 출처를 남겨주세요.