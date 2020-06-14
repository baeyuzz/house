<template>
  <div>
    <button @click="addr">asdfasdf</button>
    <div id="geocoder-error">
      <h1>Geocoder Error</h1>
    </div>
    <div id="update-error">
      <h1>Update Error</h1>
    </div>
  </div>
</template>

<script>
import $ from "jquery";
import http from "@/http-common.js";

export default {
  mounted() {
    if (window.kakao && window.kakao.maps) {
      console.log("kakao exist");
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
      console.log("kakao.maps loaded");
    },
    addr() {
      http
        .get("/rest/house/houseaddress")
        .then(response => {
          let list = response.data;

          console.log(list.length);

          this.makeLatLng(list, 0, list.length);
        })
        .catch(() => {
          alert("주소 가져오기 실패");
        });
    },
    makeLatLng(list, index, len) {
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      let vue = this;
      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(list[index], function(result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let lat = result[0].y;
          let lng = result[0].x;

          http
            .put("/rest/house/addrtolng", {
              lat: lat,
              lng: lng,
              address: list[index]
            })
            .then(response => {
              if (!response.data) {
                let $err = $("#update-error");
                $err.append($("<p>" + list[index] + "</p>"));
              }

              // 성공이든 실패든 다음 주소에 대해서도 하기
              if (index + 1 < len) {
                vue.makeLatLng(list, index + 1, len);
              }
            })
            .catch(() => {
              let $err = $("#update-error");
              $err.append($("<p>" + list[index] + "</p>"));

              // 오류났어도 다음 주소에 대해서도 하기
              if (index + 1 < len) {
                vue.makeLatLng(list, index + 1, len);
              }
            });
        } else {
          let $err = $("#geocoder-error");
          $err.append($("<p>" + list[index] + "</p>"));

          // 좌표 가져오기 실패했어도 다음 주소에 대해서 하기
          if (index + 1 < len) {
            vue.makeLatLng(list, index + 1, len);
          }
        }
      });
    }
  }
};
</script>

<style scoped>
</style>