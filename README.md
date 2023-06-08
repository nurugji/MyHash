# MyHash
software design assignment

내가 풀고싶은 문제의 사진을 가져와 여러가지 메타 정보를 입력하여 문제를 푼 후에 답을 확인하여 문제 풀이 기록을 생성할 수 있다.
내가 등록한 문제들은 태그를 사용하여 여러가지 분류 기준으로 분류할 수 있으며 검색을 통해 찾을 수도 있다.
각 문제들과 태그는 문제집에서 독립적으로 사용되며 여러개의 문제집을 생성할 수 있다

## WorkbookFrame
초기 데이터인 datastructure 문제집이 제공된다.
문제집을 새로 만들고 싶다면 add, 해당 문제집으로 들어가려면 load 를 눌러준다
![image](https://github.com/nurugji/MyHash/assets/75533765/785f49c7-7a31-4b41-8891-7ee387658a4b)

## MainFrame
초기 데이터인 datastructur 문제집의 내용이다.
새로 문제집을 만들었다면 빈 문제집이 출력될 것이다. 각 열들의 이름을 클릭하면 오름차순/내림차순으로 정렬된다
![image](https://github.com/nurugji/MyHash/assets/75533765/799bc1a4-33bd-47bb-8a39-4fc9ebddd922)

### NORTH
1. text field를 통해서 문제를 검색할 수 있다. 모든 열에 대해 검색할 수 있다
  ![image](https://github.com/nurugji/MyHash/assets/75533765/0064cb00-521c-44d8-b665-c98d163f27d9)

2. init을 통해서 text field를 초기화할 수 있다.
3. Filter를 통해서 검색할 문제의 태그를 선택할 수 있다. (FilterFrame)
4. Save를 통해서 현재 문제들을 저장할 수 있다.
  경로는 MyHash 프로젝트 폴더의 database 폴더의 문제집 이름의 폴더의 Problem.txt에 저장된다

---

### SOUTH
5. Add Problem 를 통해서 새로운 문제를 생성할 수 있다 (AddProblemFrame)
6. Edit Problem 를 통해서 기존 문제를 수정할 수 있다 (EditProblemFrame)
  ![image](https://github.com/nurugji/MyHash/assets/75533765/1984d490-d942-4265-940e-2d7e5c8ef69a)
  이미지는 직접 폴더에서 불러올 수도 있으며 붙여넣기를 사용하여 클립보드에 있는 이미지를 가져올 수 도 있다.
  클립보드에 있는 이미지는  MyHash 프로젝트 폴더의 database 폴더의 cliopboard 폴더에 날짜를 이름하여 저장된다.
  선택할 수 있는 태그는 TagManagement를 통해서 생성된 태그만이 표시된다. 즉, 문제를 생성하기 이전에 태그를 먼저 생성해 두어야 한다.
7.Delete Problem 를 통해서 기존 문제를 삭제할 수 있다

---


8. Detail를 통해서 문제를 풀 수 있다(DeatilFrame)
  문제 사진을 크게 볼 수 있으며 solve, memo를 통해서 정답과 메모를 확인할 수 있다.
  정답 기록을 관리할 수 있다. 정답/오답을 선택하여 기록할 수 있다.(HistoryFrame)
  ![image](https://github.com/nurugji/MyHash/assets/75533765/3477a4e3-9547-45c9-b77f-e278717e8f4a)

---


9. TagManagement 를 통해서 문제집의 태그를 관리할 수 있다.(TagFrame)
  생성한 태그들은 문제집에서 고유하며 문제를 분류하는데 사용된다. 각 태그를 가지고 있는 문제수도 출력하며 열의 이름을 클릭하면 오름차순/내림차순으로 정렬된다.
  ![image](https://github.com/nurugji/MyHash/assets/75533765/12c7dd73-bab6-4e4d-b80d-7599b3a26f43)

