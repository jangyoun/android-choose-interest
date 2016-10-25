# android-choose-interest

<img src="https://github.com/jangyoun/android-choose-interest/raw/master/preview.gif" width="250">

##Description
 - Using [MultiViewPager Lib] (https://github.com/Pixplicity/MultiViewPager)
 - Using [http JSON DATA] (http://leejangyoun.com/android/dummy/MultiViewPagerArr_1.json)
 - Using [Blur Effect] (https://github.com/PomepuyN/BlurEffectForAndroidDesign)
 
 - HTTP 통신
   - json Array 갯수 만큼 PagerAdapter에 추가함
   - page 적용 : json data의 last 인자를 통해, 마지막 페이지를 인지함
   - Pager의 마지막 page에 도달하면, HTTP reload 함

 - 기타 
   - http 통신의 경우, volley lib (stringrequest) 사용함
   - glide image loader lib 사용함
   - JSON parser, json lib 사용함
