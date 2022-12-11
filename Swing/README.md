<포인트 먹기 게임>
몬스터들을 피해가며 포인트를 먹는 게임
<게임 설명>
시작 생명 : 3개
포인트 하나 당 + 100점
몬스터 피격 시 생명 - 1 그리고 1.5초 동안 분노 상태 (무적 + 이동 속도 증가) 진행 시간이 40초가 지날 때 마다 플레이어와 몬스터의 속도 증가 (Level + 1) 생명이 0이 되면 게임이 멈추고 최종점수 출력
[아이템]
1. 베터리 : 700점 마다 랜덤 위치에 생성 (4초동안 분노 상태)
2. 구급상자 : 생명이 줄어든 상태라면 1500점마다 랜덤 위치에 생성 (생명 + 1)

 <주요 소스 코드 설명> 
 1.Public void go()
 게임 시작시 player와 point의 첫 위치를 설정해주었다. player은 화면의 중앙으로, point는 랜덤으로 정하 되 스코어보드 범위와 겹치게 되면 다시 랜덤으로 뽑아낸다.
time변수에 루프 당 SPEED / 1000.0f를 더해주어 진행 시간을 체크하였고, 40초마다 speedUp++ 해주었 다.
while루프에선 hitGodMod(몬스터 피격 시의 분노 상태)를 설정하기 위해 SPEED / 1000.0f를 루프가 돌 아갈 때마다 godtime(초기값 = GOD_TIME(1.5f))에서 빼주었다.(godtime값 만큼의 시간을 체크한다)
godtime이 0이되면 GodMod를 false로 바꿔주고 godtime을 GOD_TIME으로 초기화시켜준다. (똑같은 방 식으로 itemGodMod도 구현)
hitGodMod나 itemGodMod가 true이면 움직이는 stepX, stepY크기를 두배만큼 늘려준다. 마지막 if문으로 만약 생명이 0이되면 break를 통해 while문을 빠져나온다.

2.Private void moveTraps() (몬스터들의 이동과 피격을 구현)
몬스터들은 벽에 부딪히면 랜덤한 방향과 속도(0~2의 난수를 뽑아내고 오른쪽, 아래 벽에서는 -1을 곱해
주어 반대로 이동하도록 한다)로 이동한다. 레벨에 따라 곱해지는 speedUp 값이 늘어나 속도가 증가한 다.
player와 몬스터의 좌표가 겹치게 되면 hitGodMod를 true로 바꾸고 생명을 -1한다.

3.class Key (플레이어 이동)
playerMove() 메소드에서 상하좌우 대각선 이동을 구현하기 위해 해당 방향의 boolean 변수를 사용하였 다. 방향키가 눌러지면 해당 방향의 변수를 true로 때면 false로 바꿔주고 if문으로 변수 값에 따른 stepX 와 stepY의 값을 정해주었다
  
 4.class GamePanel (그리기)
문자는 drawString으로 그렸고 이미지는 drawImage를 통해 그려주었다
itemDrop, healDrop의 값에 따라 아이템들을 그려주었고 end가 true이면 GAME OVER와 함께 최종 점수 를 그렸다.
