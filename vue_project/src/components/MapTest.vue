<template>
  <div>
    <div id="map" style="width:500px;height:400px;"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      map: {}
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      /* global kakao */
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=c0153be2e7c8003d17fd11e5d2f1dcfe&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      /*
        1. 지도 만들어서 화면에 붙이기
    */
      // 지도를 담을 컨테이너를 가져옵니다
      var container = document.getElementById("map");
      // 지도에 옵션을 설정합니다
      var options = {
        // 처음 표시될 좌표
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        // 처음 표시될 확대/축소 레벨
        level: 3
      };

      // 지도를 만들어 컨테이너에 붙입니다.
      var map = new kakao.maps.Map(container, options);

      // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

      this.map = map;

      /*
        지도 이동시키기
    */
      //   // 이동할 위도 경도 위치를 생성합니다
      //   var moveLatLon = new kakao.maps.LatLng(33.45058, 126.574942);

      //   // 지도 중심을 부드럽게 이동시킵니다
      //   // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
      //   map.panTo(moveLatLon);

      /*
        마커 표시하기
    */
      // 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다
      var positions = [
        {
          content: "<div>카카오</div>",
          latlng: new kakao.maps.LatLng(33.450705, 126.570677)
        },
        {
          content: "<div>생태연못</div>",
          latlng: new kakao.maps.LatLng(33.450936, 126.569477)
        },
        {
          content: "<div>텃밭</div>",
          latlng: new kakao.maps.LatLng(33.450879, 126.56994)
        },
        {
          content: "<div>근린공원</div>",
          latlng: new kakao.maps.LatLng(33.451393, 126.570738)
        }
      ];

      for (var i = 0; i < positions.length; i++) {
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          map: map, // 마커를 표시할 지도
          position: positions[i].latlng // 마커의 위치
        });

        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: positions[i].content // 인포윈도우에 표시할 내용
        });

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        kakao.maps.event.addListener(
          marker,
          "mouseover",
          this.makeOverListener(map, marker, infowindow)
        );
        kakao.maps.event.addListener(
          marker,
          "mouseout",
          this.makeOutListener(infowindow)
        );
        kakao.maps.event.addListener(marker, "click", this.makeClickListener());
      }

      //   // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
      //   var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      //     iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      //   // 인포윈도우를 생성합니다
      //   var infowindow = new kakao.maps.InfoWindow({
      //     content: iwContent,
      //     removable: iwRemoveable
      //   });

      //   // 마커에 클릭이벤트를 등록합니다
      //   kakao.maps.event.addListener(marker, "click", function() {
      //     // 마커 위에 인포윈도우를 표시합니다
      //     infowindow.open(map, marker);
      //   });

      /*
        현재 위치 얻어와서 마커로 표시하기
    */
      //   // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
      //   if (navigator.geolocation) {
      //     // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      //     navigator.geolocation.getCurrentPosition(function(position) {
      //       var lat = position.coords.latitude, // 위도
      //         lon = position.coords.longitude; // 경도

      //       var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
      //         message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다

      //       // 마커와 인포윈도우를 표시합니다
      //       displayMarker(locPosition, message);
      //     });
      //   } else {
      //     // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

      //     var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
      //       message = "geolocation을 사용할수 없어요..";

      //     displayMarker(locPosition, message);
      //   }

      /*
        주소로 좌표 얻어내기
      */
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      // 주소로 좌표를 검색합니다
      geocoder.addressSearch("제주특별자치도 제주시 첨단로 242", function(
        result,
        status
      ) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

          // 결과값으로 받은 위치를 마커로 표시합니다
          var marker = new kakao.maps.Marker({
            map: map,
            position: coords
          });

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          var infowindow = new kakao.maps.InfoWindow({
            content:
              '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
          });
          infowindow.open(map, marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          map.setCenter(coords);
        }
      });
    },

    // 여러개의 마커에 이벤트를 달기 위해 필요한 함수
    makeOverListener(map, marker, infowindow) {
      return function() {
        infowindow.open(map, marker);
      };
    },
    makeOutListener(infowindow) {
      return function() {
        infowindow.close();
      };
    },
    makeClickListener() {
      return function() {
        alert("Click!");
      };
    }
  }
};
</script>

<style scoped>
</style>