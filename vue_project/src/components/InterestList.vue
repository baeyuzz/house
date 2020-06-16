<template>
  <div id="root-template-box">
    <div class="mt-3 row">
      <!-- 관심지역 목록 -->
      <button class="btn btn-danger ml-5" @click="grepInterestRegion">집 나간 관심지역정보 데려오기</button>
      <div id="sidebox" class="interest-table">
        <div class="table-responsive">
          <table class="table table-hover table-bordered">
            <thead>
              <tr>
                <th colspan="4">관심지역 정보</th>
              </tr>
            </thead>
            <tbody>
              <template v-if="regions.length > 0">
                <template v-for="(region, index) in regions">
                  <tr :key="region.no">
                    <td>
                      <div style="float : left">{{region.sigungu}}</div>
                      <div style="text-align : right">
                        <a href="#" @click.prevent="searchEnv(index)">
                          환경
                          <i class="fa fa-search" aria-hidden="true"></i>
                        </a>
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        <a
                          href="#"
                          @click.prevent="searchShop(index)"
                        >
                          상권
                          <i class="fa fa-search" aria-hidden="true"></i>
                        </a>
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        <a
                          href="#"
                          @click.prevent="removeRegion(index)"
                        >
                          <i class="fa fa-trash" aria-hidden="true"></i>
                        </a>
                      </div>
                    </td>
                  </tr>
                </template>
              </template>
              <template v-else>
                <tr>
                  <td>등록된 관심지역이 없습니다.</td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 환경, 상권정보 -->
      <div class="information-table mt-3 offset-3 col-9">
        <template v-show="envs.length > 0 || shops.length > 0">
          <!-- 지도 -->
          <div class="container map-box">
            <div id="map"></div>
          </div>

          <!-- 상점 카테고리 -->
          <div v-show="shops.length > 0" class="category">
            <ul>
              <li id="foodMenu" @click="changeMarker('food')">
                <span class="ico_comm ico_food"></span>
                음식
              </li>
              <li id="lifeMenu" @click="changeMarker('life')">
                <span class="ico_comm ico_life"></span>
                생활서비스
              </li>
              <li id="shopMenu" @click="changeMarker('shop')">
                <span class="ico_comm ico_shop"></span>
                소매
              </li>
              <li id="studyMenu" @click="changeMarker('study')">
                <span class="ico_comm ico_study"></span>
                학문/교육
              </li>
              <li id="houseMenu" @click="changeMarker('house')">
                <span class="ico_comm ico_house"></span>
                부동산
              </li>
              <li id="playMenu" @click="changeMarker('play')">
                <span class="ico_comm ico_play"></span>
                관광/여가/오락
              </li>
              <li id="sleepMenu" @click="changeMarker('sleep')">
                <span class="ico_comm ico_sleep"></span>
                숙박
              </li>
            </ul>
          </div>

          <!-- 상점, 환경 정보 테이블(내부 내용반 조건에 따라 바뀜) -->
          <div v-if="envs.length > 0 || shops.length > 0" class="container table-box">
            <div class="table-responsive info-table">
              <table class="table table-hover table-bordered">
                <!-- 환경 정보로 내용 채우기 -->
                <template v-if="envs.length > 0">
                  <thead>
                    <tr>
                      <th @click="sortEnv('name')">사업체 이름</th>
                      <th @click="sortEnv('type')">환경영향타입</th>
                      <th @click="sortEnv('address')">주소</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="env in envs" v-bind:key="env.no">
                      <td>{{env.name}}</td>
                      <td>{{env.type}}</td>
                      <td>{{env.address}}</td>
                    </tr>
                  </tbody>
                </template>

                <!-- 상권 정보로 내용 채우기 -->
                <template v-if="shops.length > 0">
                  <thead>
                    <tr>
                      <th @click="sortShop('name')">사업체 이름</th>
                      <th @click="sortShop('type4')">업종 분류</th>
                      <th @click="sortShop('address')">주소</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      v-for="(shop, index) in shops"
                      v-bind:key="shop.no"
                      @click="clickShop(index)"
                    >
                      <td :title="shop.name">{{shop.name}}</td>
                      <td :title="shop.type4">{{shop.type4}}</td>
                      <td :title="shop.address">{{shop.address}}</td>
                    </tr>
                  </tbody>
                </template>
              </table>
            </div>
          </div>
          <div v-show="envs.length > 0 || shops.length > 0" class="container chart-box">
            <canvas id="chart"></canvas>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/http-common.js";
import Chart from "chart.js";
import $ from "jquery";

export default {
  data() {
    return {
      regions: [],
      shops: [],
      envs: [],

      curIdx: -1,

      mapList: [],
      map: {},
      mapCreated: false,
      mapCreate: 0,

      shopMarkers: [],
      shopSetMarkers: []
    };
  },
  watch: {
    mapCreate() {
      console.log("init map in mapCreate watcher called!");
      this.initMap();
      this.mapCreated = true;
    }
  },
  computed: {
    id() {
      return this.$store.state.id;
    }
  },
  created() {
    this.getRegionList();
  },
  // Mounted : 카카오 맵 API 를 사용하기 위함
  mounted() {
    if (window.kakao && window.kakao.maps) {
      if (!this.mapCreated && this.mapList.length > 0) {
        console.log("initMap in mounted is Called!");
        this.initMap();
        this.mapCreated = true;
      }
    } else {
      /* global kakao */
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=c0153be2e7c8003d17fd11e5d2f1dcfe&libraries=services";
      document.head.appendChild(script);
    }
  },
  // scroll 이벤트 등록을 위해
  updated() {
    let currentPosition = parseInt($("#sidebox").css("top"));

    $(window).scroll(function() {
      let position = $(window).scrollTop();
      $("#sidebox")
        .stop()
        .animate(
          {
            top: position + currentPosition + "px"
          },
          1000
        );
    });
  },
  methods: {
    searchEnv(index) {
      this.curIdx = index;
      console.log("searchEnv: " + this.regions[index].sigungu);
      http
        .post("/rest/env", {
          sigudong: this.regions[index].sigungu
        })
        .then(response => {
          this.envs = response.data.list;
          this.shops = [];

          // 환경정보는 맵 안쓰니까 숨기기
          $("#map").hide();

          // 차트 만들기
          this.makeChart(response.data.chart, "env");
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    searchShop(index) {
      this.curIdx = index;
      console.log("searchShop: " + this.regions[index].sigungu);
      http
        .post("/rest/shop", {
          sigudong: this.regions[index].sigungu
        })
        .then(response => {
          this.shops = response.data.list;
          this.envs = [];

          // 맵 만들기
          this.mapList = this.shops;
          this.mapCreate++;

          // chart 만들기
          this.makeChart(response.data.chart, "shop");
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    makeChart(chartData, gubun) {
      // 라벨, 데이터 만들기
      let labels = [];
      let data = [];
      for (let d of chartData) {
        labels.push(d.type);
        data.push(d.count);
      }

      let label = '';
      if(gubun == "shop") {
        label = "상권 분포";
      } else {
        label = "환경 영향 분포";
      }

      let ctx = $("#chart");
      new Chart(ctx, {
        type: "radar",
        data: {
          labels: labels,
          datasets: [
            {
              data: data,
              label: label,
              backgroundColor: "#ccebff",
              borderColor: "#0099ff",
              pointBackgroundColor: "#003d66"
            }
          ]
        }
      });
    },
    clickShop(index) {
      console.log(index);
    },
    sortShop(by) {
      http
        .post("/rest/shop/sort", {
          list: this.shops,
          by: by
        })
        .then(response => {
          this.shops = response.data;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    sortEnv(by) {
      http
        .post("/rest/env/sort", {
          list: this.envs,
          by: by
        })
        .then(response => {
          this.envs = response.data;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    removeRegion(index) {
      if (index == this.curIdx) {
        this.shops = [];
        this.envs = [];
        this.mapList = [];

        $("#map").hide();
      }
      http
        .post("/rest/interest/delete", {
          sigungu: this.regions[index].sigungu,
          userid: this.regions[index].userid
        })
        .then(response => {
          if (response.data) {
            alert("관심지역이 삭제되었습니다");
          } else {
            alert("관심지역 삭제 실패");
          }
          this.getRegionList();
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    getRegionList() {
      http
        .get("/rest/interest/" + this.id)
        .then(response => {
          this.regions = response.data;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    initMap() {
      if (this.mapList.length == 0) return;

      if (this.shops.length > 0) {
        this.initMapForShop();
      } else {
        this.initMapForEnv();
      }
    },
    initMapForShop() {
      // 이걸 먼져 해야한대요
      $("#map").show();

      // 지도를 담을 컨테이너를 가져옵니다
      var container = document.getElementById("map");
      // 지도에 옵션을 설정합니다
      var options = { level: 3 };
      if (this.mapList.length > 0) {
        options.center = new kakao.maps.LatLng(
          this.mapList[0].lat,
          this.mapList[0].lng
        );
      }

      // 지도를 만들어 컨테이너에 붙입니다.
      var map = new kakao.maps.Map(container, options);
      console.log("Add map on dom complete");

      // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
      console.log("Add control on map complete");

      this.map = map;

      // 각 카테고리 별 마커의 위치가 저장될 리스트
      let positions = [];

      // 각 카테고리 별 마커를 저장할 리스트 초기화
      this.shopMarkers = [];
      for (let i = 0; i < 7; i++) {
        this.shopMarkers.push([]);

        positions.push([]);
      }

      var bounds = new kakao.maps.LatLngBounds();
      for (let shop of this.mapList) {
        let obj = {};
        obj.latlng = new kakao.maps.LatLng(shop.lat, shop.lng);
        obj.content =
          '<div class="kakao-wrap">' +
          '    <input id="kakao-no" type="hidden" value=' +
          shop.no +
          "/>" +
          '    <div class="kakao-info">' +
          '        <div class="kakao-title">' +
          shop.name +
          "        </div>" +
          '        <div class="kakao-body">' +
          '            <div class="kakao-desc">' +
          '                <div class="kakao-ellipsis">' +
          shop.address +
          "</div>" +
          "            </div>" +
          "        </div>" +
          "    </div>" +
          "</div>";
        obj.trickVal = shop.no;

        // 각 카테고리별 마커 좌표 배열 셋팅
        if (shop.type1 == "음식") {
          positions[0].push(obj);
        } else if (shop.type1 == "생활서비스") {
          positions[1].push(obj);
        } else if (shop.type1 == "소매") {
          positions[2].push(obj);
        } else if (shop.type1 == "학문/교육") {
          positions[3].push(obj);
        } else if (shop.type1 == "부동산") {
          positions[4].push(obj);
        } else if (shop.type1 == "관광/여가/오락") {
          positions[5].push(obj);
        } else if (shop.type1 == "숙박") {
          positions[6].push(obj);
        }

        // 모든 마커가 다 보이는 형태로 지도 셋팅
        bounds.extend(obj.latlng);
      }
      map.setBounds(bounds);

      var markerImageSrc = "http://127.0.0.1:8080/img/shop_category.png";

      // 아래 Marker 생성에서 쓰일 함수 두개
      // 마커이미지의 주소와, 크기, 옵션으로 마커 이미지를 생성하여 리턴하는 함수입니다
      function createMarkerImage(src, size, options) {
        var markerImage = new kakao.maps.MarkerImage(src, size, options);
        return markerImage;
      }

      // 좌표와 마커이미지를 받아 마커를 생성하여 리턴하는 함수입니다
      let vue = this;
      function createMarker(position, image) {
        // 마커 생성
        var marker = new kakao.maps.Marker({
          position: position.latlng,
          image: image
        });

        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: positions.content // 인포윈도우에 표시할 내용
        });
        infowindow.trickVal = positions.trickVal;

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        kakao.maps.event.addListener(
          marker,
          "mouseover",
          vue.makeOverListener(map, marker, infowindow)
        );
        kakao.maps.event.addListener(
          marker,
          "mouseout",
          vue.makeOutListener(infowindow)
        );
        kakao.maps.event.addListener(
          marker,
          "click",
          vue.makeClickListener(infowindow)
        );

        return marker;
      }

      // Narker 들 생성
      let point_h = 0;
      for (let seo_i = 0; seo_i < 7; seo_i++) {
        let poss = positions[seo_i];
        for (var i = 0; i < poss.length; i++) {
          var imageSize = new kakao.maps.Size(22, 26),
            imageOptions = {
              spriteOrigin: new kakao.maps.Point(10, point_h),
              spriteSize: new kakao.maps.Size(36, 250)
            };

          // 마커이미지와 마커를 생성합니다
          var markerImage = createMarkerImage(
              markerImageSrc,
              imageSize,
              imageOptions
            ),
            marker = createMarker(poss[i], markerImage);

          // 생성된 마커를 마커 배열에 추가합니다
          this.shopMarkers[seo_i].push(marker);
        }
        point_h += 36;
      }

      // 기본적으로 food 마커들이 보이게 함
      this.changeMarker("food");
      console.log("Add all marker complete");
    },
    initMapForEnv() {
      console.log("Map for env");
    },
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
    makeClickListener(infowindow) {
      return function() {
        console.log(infowindow.trickVal);
      };
    },
    setMarkers(map, idx) {
      let markers = this.shopMarkers[idx];
      for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
      }
    },
    changeMarker(type) {
      var foodMenu = document.getElementById("foodMenu");
      var lifeMenu = document.getElementById("lifeMenu");
      var shopMenu = document.getElementById("shopMenu");
      var studyMenu = document.getElementById("studyMenu");
      var houseMenu = document.getElementById("houseMenu");
      var playMenu = document.getElementById("playMenu");
      var sleepMenu = document.getElementById("sleepMenu");

      let menus = [
        foodMenu,
        lifeMenu,
        shopMenu,
        studyMenu,
        houseMenu,
        playMenu,
        sleepMenu
      ];

      let targ = 0;
      if (type == "food") targ = 0;
      else if (type == "life") targ = 1;
      else if (type == "shop") targ = 2;
      else if (type == "study") targ = 3;
      else if (type == "house") targ = 4;
      else if (type == "play") targ = 5;
      else if (type == "sleep") targ = 6;

      for (let i = 0; i < 7; i++) {
        if (i == targ) {
          menus[i].className = "menu_selected";
          this.setMarkers(this.map, i);
        } else {
          menus[i].className = "";
          this.setMarkers(null, i);
        }
      }
    },
    grepInterestRegion() {
      $("#sidebox").css("top", 433);
    }
  }
};
</script>

<style scoped>
#adddiv {
  text-align: right;
}
#page {
  text-align: center;
}
.info-table {
  height: 500px;
  border: 1px solid black;
}

th,
td {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

/* 움직이는 사이드바 출처: https://88240.tistory.com/171 [shaking blog] */
#sidebox {
  background-color: white;
  position: absolute;
  top: 433px;
  left: 20px;
  width: 350px;
  max-height: 800px;
}

#sidebox th {
  background-color: aquamarine;
}

#map {
  height: 600px;
}
.map-box {
  margin-top: 80px;
  margin-bottom: 20px;
}

.map-box,
.table-box,
.chart-box {
  width: 1300px;
}

#chart {
  margin: 20px;
}

/* 카카오 맵 API 출처 카테고리 리스트 */
.category,
.category * {
  margin: 0;
  padding: 0;
  color: #000;
}
.category {
  position: absolute;
  overflow: hidden;
  top: 10px;
  left: 65px;
  width: 570px;
  height: 60px;
  z-index: 10;
  border: 1px solid black;
  font-family: "Malgun Gothic", "맑은 고딕", sans-serif;
  font-size: 12px;
  text-align: center;
  background-color: #fff;
}
.category .menu_selected {
  background: #ff5f4a;
  color: #fff;
  border-left: 1px solid #915b2f;
  border-right: 1px solid #915b2f;
  margin: 0 -1px;
}
.category li {
  list-style: none;
  float: left;
  width: 80px;
  height: 60px;
  padding-top: 10px;
  cursor: pointer;
}
.category .ico_comm {
  display: block;
  margin: 0 auto 2px;
  width: 22px;
  height: 26px;
  background: url("http://127.0.0.1:8080/img/shop_category.png") no-repeat;
}
.category .ico_food {
  background-position: -10px 0;
}
.category .ico_life {
  background-position: -10px -36px;
}
.category .ico_shop {
  background-position: -10px -72px;
}
.category .ico_study {
  background-position: -10px -108px;
}
.category .ico_house {
  background-position: -10px -144px;
}
.category .ico_play {
  background-position: -10px -180px;
}
.category .ico_sleep {
  background-position: -10px -216px;
}
</style>