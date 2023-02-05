# 이게 뭐지?
<h3>Android</h3>
던파 피로 회복 영약의 가격 알림 <br>
영약의 하위 재료들 가격 알림 <br>
<br>
[피로 회복 영약 가격 - 경매장 수수료 > 재료들 가격 총합] 일 경우 알리미 <br>
알림 방법 : 앱 안에서는 가격 배경 색깔로 표시, 앱에서 나갔을 경우 알림 및 진동으로 표시 <br>
<br>

심심해서 만든 개인 첫 작품


# 필요한 재료들
- DNF Api Key
- FireBase Server Key
- Device Token [GetDeviceToken 함수로 볼 수 있게끔 해놓음]

# 사용 환경
Android Studio <br>
Emulator : Pixel 4 API 29 <br>
SDK : API 31

# 사용법
MainActivity에서 dnfApiKey, firebaseKey, deviceToken 변수 수정 및 실행 <br>
필요 시 title, body 변수도 수정

# 주의사항
- 자신의 api를 여러 곳에서 사용하면 충돌
- 앱 안에서는 알림 작동 X, 백그라운드 상태에서만 O
- 던파 게임 내 경매장과 API는 딜레이가 존재. 그래서 실제 경매장과는 가격, 물량이 약간 차이남
- 원래는 <strong>노련한 영혼의 정수</strong>를 사용하는데 이 앱은 <strong>노련한 영혼의 정수 하위 재료</strong>들로 계산
- 재료 총합은 [재료 x 개수] 들의 합

# 피로 회복 영약 레시피
- 황금 큐브 조각 3개
- 생명의 숨결 20개
- 노련한 모험가의 정수 9개
- 마력 결정 1개
- 무색 마력의 산물 1개
