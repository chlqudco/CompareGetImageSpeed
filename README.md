## 결과 스포일러

- ### 방법 1. LayoutInflater로 무작정 받기 (이미지 : 6000개)

| |LayoutInflater|
|---|------|
|1회차|12.5초|
|2회차|12.5초|
|3회차|19.8|

<br/>

- ### 방법 2. RecyclerView 사용 (이미지 : 10000개)

| |Grid 1줄|Grid 10줄|
|---|------|---|
|1회차|12.4초|8.8초|
|2회차|8.7초|8초|
|3회차|6초|8.5초|

<br/>

- ### 방법 3. Coroutine 사용

| |RecyclerView 이용(이미지 : 10000개)|LayoutInflater 이용(이미지 : 6000개)|
|---|------|---|
|1회차|11초|13.7초|
|2회차|6.8초|13.3초|
|3회차|4.8초|13.5초|

<br/>

- ### 방법 4. Jetpack Paging 사용 (이미지 : 10000개)

| |RecyclerView 이용|
|---|------|
|1회차|0.6초|
|2회차|0.5초|
|3회차|0.5초|

<br/>

---


<br/>


- 도입
	- 만개의 이미지를 가장 빠르거나 효율적으로 불러 올 수 있는 방법을 알아보고자 시작한 프로젝트
	- 이미지를 불러올 수 있는 방법은 너무나 많다! 나는 그 중에 최선의 방법을 찾고 싶다! 왜? UX는 매우 중요하니까!!
  
<br/>


- 궁리
	- 간단하게 받아오기만 하면 되므로 디자인 패턴은 쓰지 말자.
	- 내가 아는 방법을 모두 모아보자
	- 이미지 만개는 어디서 가져오지? mocky 사이트에서 json 응답을 직접 만들어보자!
	- https://run.mocky.io/v3/db07afc5-ee37-4c63-9b83-6a1edf3f71ec
	- paging은 백엔드 친구에게 부탁했기 때문에 따로 url을 올릴 수 없습니다.
  
<br/>


- 시간 측정 기준은?
	- 받아오기 라는 버튼을 만들자
	- 내가 직접 스탑워치로 받아오기 버튼 클릭할 때 시작하고 UI에 보여졌을 때 Stop 해보자.
	- UX적인 측면에선 이게 가장 합리적이라고 생각한다.
  
<br/>


- 테스트 환경
	- 갤럭시 S20 Ultra
	- 5G 환경

<br/>


- 방법
	- 그냥 무작정 받아오기 (리사이클러 뷰 안쓰고 LayoutInflater)
	- 그냥 무작정 받아오기 (리사이클러 뷰 사용)
	- 코루틴을 이용하기
	- paging을 이용하기
	- 생각 나면 추후 추가 예정


<br/>


- 테스트 시작!

---

<br/>



- 그냥 무작정 받아오기 (리사이클러 뷰 안쓰고 LayoutInflater)
	- xml에 스크롤뷰와 리니어 레이아웃 하나만 있다. LayoutInflater를 사용해 하나씩 뷰를 동적으로 추가하는 방법이다.
	- 내가 생각할 수 있는 최악의 방법이다. 만개를 넣으려고 하니 넣어지지도 않는 최악의 방법이다. 
	- 결과는 당연한 말이지만 정말 느리다.
	- 결과(6000개) : 12.5초, 12.5초, 19.8초
	- 근데 신기한건 어쩔 땐 8천개가 성공하고 어쩔땐 7천개도 실패한다는 것이다.
	- 어쨋든 내 핸드폰 상황 문제인지 7천개 부터는 OutOfMemoryError가 뜨는 경우가 많다.
	- 혹시 Volley를 이용하면 더 빠를까? 궁금해져서 검색을 시작했다.
	- 도대체 시간차이는 왜 나는걸까?


<br/>


- Volley vs Retrofit
	- 

<br/>


---

<br/>


- 그냥 무작정 받아오기 (리사이클러 뷰 사용)
	- 앞으로는 Retrofit만 사용할것이다. 굳이 Volley와 비교할 필요가 없을 것 같다.
	- 이번엔 리사이클러 뷰를 사용할 것이다.
	- 리사이클러 뷰는 LayoutInflater 처럼 모든 뷰를 한꺼번에 만들지 않고 화면에 보이는 뷰홀더 개수만 만든다.
	- 따라서 더 빠른 로딩을 해주지 않을까? 라고 기대했다.
	- 여기서 중요한게 있다. 뷰홀더 개수가 적을 수록 유의미한 시간차이가 날까? 궁금해졌다.
	- 따라서 그리드레이아웃매니저를 이용했고 1줄과 10줄 등 줄 개수를 비교하며 측정해보았다
	- 1줄 일 때
		- 결과(10000개) : 12.4초, 8.7초, 6초
		- 1만개를 받아올 수 있는걸 보니 역시 뷰홀더를 줄이는게 많은 도움이 되는 것 같다.
		- 왜 삭제하고 처음받아올 땐 느리고 다시 받아올 땐 빠른거지?
	- 10줄 일 때
		- 결과(10000개) : 8.8초, 8초, 8.5초
		- 1줄 이던 10줄이던 뷰홀더 개수가 막대한 차이가 아니다보니까 별로 차이가 없는걸 볼 수 있다.
	- 100줄 일 때
		- 호기롭게 도전해본 100줄. 아무리 기다려도 화면에 사진은 나타나지 않는다.ㅠㅠ 왜일까?

<br/>


---

<br/>


- 코루틴을 이용하기
	- 이번 방법은 코루틴을 이용하기!
	- 이걸로 빠르게 받아올 방법은 전혀 생각나지 않지만 발버둥이라도 쳐본다
	- 왜냐하면 UI 변경 작업은 결국 Main 쓰레드가 담당해야 하기 때문이다.
	- 코루틴은 단순히 IO 디스패처로 Json을 불러오기 위한 용도인것 같다.
	- 
	- RecyclerView에 코루틴만 쓴 경우
		- 결과(10000개) : 11초, 6.8초, 4.8초
		- 코루틴을 쓴 게 조금 더 빠른것 같다. 왜일까?
	- 
	- RecyclerView 안쓰고 LayoutInflater 돌리기
		- 6000개의 아이템을 매번 새로운 launch 블록을 생성 한뒤 동적으로 넣었다.
		- 결과(6000개) : 13.7초, 13.3초, 13.5초
		- 정말 안좋은 방법이다.
	-
	- 내가 코루틴을 제대로 못 쓰고 있는건가? 싶은 생각이 든다.

<br/>


---

<br/>


- paging을 이용하기
	- 최신 기술! Jetpack Paging을 사용해보자!
	- 사실 이 프로젝트는 이걸 사용하기 위해 시작한 프로젝트이다.
	- 첫번째 대비 얼마나 좋은 효율이 나올지 궁금하다!
	- Paging을 위해 특별히 ListAdapter로 교체해줬다.
	- 결과(10000개) : 10.4초, 6.4초, 5.7초
	- 내가 페이징을 잘 모르는 것인가.. 이게 정상인가.. 뇌정지가 온다.
	- 엄청 빠를 줄 알았는데 ㅠㅠ 리사이클러뷰가 대단한 친구인건가..
	- 페이징은 그냥 첫 로딩이 아니라 다음 로딩을 빠르게 해주기 위한 친구인가?
	
- 해결 완료
	- api에서부터 페이징을 제공해줘야 한다는 것을 깨달았다. 난 정말 바보였구나.
	- 그래서 백엔드를 하는 친구를 섭외해서 paging을 지원하는 api를 만들어달라고 부탁했다.
	- 적용해보니 왠걸!!! 1초도 안걸리게 되었다!! 페이징 만세!! 페이징은 신이야!!

<br/>


---

<br/>


- 결론
	- LayoutInflater보다는 왠만하면 RecyclerView를 써야겠다.
	- 결과가 시간에 따라 인터넷 환경에 따라 천차만별이라 뭐가 제일 좋다고 말하기 어려울 것 같다.
	- 페이징을 조금 더 공부한 뒤에 다시 업그레이드 해보고 싶다.
	- 업그레이드 완료!!! 페이징 최고!!!!


<br/>



