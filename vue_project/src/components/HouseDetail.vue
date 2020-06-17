<template>
  <div class="container-fluid">
    <br />
    <div class="row">
      <div class="map-news-box col-4 container right-box">
        <div class="maps">
          <div id="map-container">
            <div id="rvWrapper">
              <div id="roadview" style="width:100%;height:100%;"></div>
              <!-- 로드뷰를 표시할 div 입니다 -->
            </div>
            <div id="mapWrapper">
              <div id="map" style="width:100%;height:100%"></div>
              <!-- 지도를 표시할 div 입니다 -->
              <div id="roadviewControl" @click="setRoadviewRoad"></div>
            </div>
          </div>
        </div>
        <button class="btn btn-primary mt-3" @click="reposition">지도를 건물 위치로 이동</button>
      </div>

      <!-- 건물 정보 -->
      <div class="container content-box col-3 align-self-center">
        <div class="info-box table-responsive">
          <table class="table">
            <thead>
              <tr>
                <td colspan="2">
                  <h3>
                    <i class="fas fa-building"></i> 건물 거래 정보
                  </h3>
                </td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th>주소</th>
                <td :title="house.address">{{house.address}}</td>
              </tr>
              <tr>
                <th>아파트 이름</th>
                <td :title="house.aptName">{{house.aptName}}</td>
              </tr>
              <tr>
                <th>건축 년도</th>
                <td :title="house.buildYear">{{house.buildYear}}년</td>
              </tr>
              <tr>
                <th>면적</th>
                <td :title="house.area">{{house.area}}㎡ ({{this.pyung}}평)</td>
              </tr>
              <tr>
                <th>층</th>
                <td :title="house.floor">{{house.floor}}층</td>
              </tr>
              <tr>
                <th>거래 유형</th>
                <td :title="house.typeString">{{house.typeString}}</td>
              </tr>
              <tr>
                <th>거래 금액</th>
                <td :title="house.dealAmount">{{house.dealAmount}} (단위 : 천원)</td>
              </tr>
              <tr v-if="house.type > 2">
                <th>임대료</th>
                <td :title="house.rentMoney">{{house.rentMoney}} (단위 : 만원) </td>
              </tr>
              <tr>
                <th>거래 날짜</th>
                <td :title="house.dealDate">{{house.dealDate}}</td>
              </tr>
            </tbody>
          </table>

          <router-link class="btn btn-info" to="/">메인으로 돌아가기</router-link>
        </div>
      </div>

      <!-- 그래프 -->
      <div class="container chart-box col-4">
        <div v-if="this.avgDeal>-1" class="chart-container">
          <canvas class="chart-canvas row" id="chart-area"></canvas>
          <div style="font-size:14px">
            <span>평균 매매가 : {{this.avgDeal}} (단위 : 천원)</span>
          </div>
        </div>
        <div v-if="this.avgRent1>-1" class="chart-container">
          <canvas class="chart-canvas row" id="chart-area-rent"></canvas>
          <div style="font-size:14px">
            <p>
              평균 보증금/전세 : {{this.avgRent1}} (단위 : 천원)
              <br />
              평균 월세 : {{this.avgRent2}} (단위 : 만원)
            </p>
          </div>
        </div>
      </div>
    </div>
    <hr />
    <!-- 뉴스,범죄그래프 -->
    <div class="row right-box mt-3 bottom-content">
      <div class="col-7" style="padding-right : 5%">
        <div id="news">
          <div><strong>관련 뉴스 보기</strong></div>
          <br />
          <div v-for="(n,index) in this.news" :key="n.no">
            <div v-if="index<3" style="float : left; font-size : 14px;">
              <a :title="n.title" v-bind:href="n.link" target="_blank">{{n.title}}</a>
              <div :title="n.description">{{n.description}}</div>
              <div class="pubDate">
                <span class="pubDate">{{n.pubDate}}</span>
              </div>
            </div>
            <br />
          </div>
        </div>
      </div>
      <div class="chart-container col-4">
        <canvas class="chart-canvas" id="crime-chart"></canvas>
      </div>
      <div class="col-1" />
    </div>
  </div>
</template>

<script>
import http from "@/http-common.js";
import Chart from "chart.js";
import $ from "jquery";
import Vue from "vue";
import VueCarousel from "vue-carousel";
Vue.use(VueCarousel);

export default {
  data() {
    return {
      no: 0,
      house: {},
      pyung : '',

      news: [],

      nos: [],
      avgDeal: "",
      avgRent1: "",
      avgRent2: "",

      map: {},
      currentPos: {},
      address: "",
      mapCreated: false,

      overlayOn: false,
      marker: {},
      container: {},
      rv: {},
      rvClient: {}
    };
  },
  created() {
    console.log("Detail created() called!");
    this.no = this.$route.params.no;
    http
      .get("/rest/house/detail/" + this.no)
      .then(response => {
        this.house = response.data.house;
        this.news = response.data.news;
        this.pyung = (this.house.area*0.3025).toFixed(2);

        // address watcher 를 부르기 위해서(카카오 맵 어려워)
        this.address = this.house.address;

        // house 정보를 잘 가져왔다면 chart 만들기 위한 정보 가져오기
        http
          .post("/rest/house/chart", {
            address: this.house.address,
            aptName: this.house.aptName,
            type: this.house.type
          })
          .then(response => {
            this.nos = response.data.nos;
            this.makeChart(response.data);
            this.avgDeal = response.data.avgDeal;
            this.avgRent1 = response.data.avgRent1;
            this.avgRent2 = response.data.avgRent2;
          })
          .catch(error => {
            alert("Error: " + error);
          });

        http
          .post("/rest/crime", {
            address: this.house.address
          })
          .then(response => {
            this.crimeChart(response.data);
          })
          .catch(error => {
            alert("Error: " + error);
          });
      })
      .catch(error => {
        alert("Error: ", error);
      });
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      if (this.address.length > 0) {
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
  watch: {
    address() {
      if (!this.mapCreated) {
        console.log("init map in address watcher called!");
        this.initMap();
      }
    }
  },
  methods: {
    initMap() {
      // 그냥 DB에 좌표 값을 가지고 있는게 편함
      this.setMap(new kakao.maps.LatLng(this.house.lat, this.house.lng));
    },
    setMap(coords) {
      this.overlayOn = false; // 지도 위에 로드뷰 오버레이가 추가된 상태를 가지고 있을 변수
      this.container = document.getElementById("map-container"); // 지도와 로드뷰를 감싸고 있는 div 입니다

      var mapContainer = document.getElementById("map"), // 지도를 표시할 div 입니다
      rvContainer = document.getElementById("roadview"); //로드뷰를 표시할 div 입니다

      this.currentPos = coords;

      let mapCenter = coords;

      // 지도에 옵션을 설정합니다
      var options = {
        // 처음 표시될 좌표
        center: coords,
        // 처음 표시될 확대/축소 레벨
        level: 3
      };

      // 지도를 만들어 컨테이너에 붙입니다.
      var map = new kakao.maps.Map(mapContainer, options);
      this.map = map;

      // 로드뷰 객체를 생성합니다
      var rv = new kakao.maps.Roadview(rvContainer);
      this.rv = rv;

      // 좌표로부터 로드뷰 파노라마 ID를 가져올 로드뷰 클라이언트 객체를 생성합니다
      var rvClient = new kakao.maps.RoadviewClient();
      this.rvClient = rvClient;

      let vue = this;

      // 마커 이미지를 생성합니다
      var markImage = new kakao.maps.MarkerImage(
        "https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png",
        new kakao.maps.Size(26, 46),
        {
          // 스프라이트 이미지를 사용합니다.
          // 스프라이트 이미지 전체의 크기를 지정하고
          spriteSize: new kakao.maps.Size(1666, 168),
          // 사용하고 싶은 영역의 좌상단 좌표를 입력합니다.
          // background-position으로 지정하는 값이며 부호는 반대입니다.
          spriteOrigin: new kakao.maps.Point(705, 114),
          offset: new kakao.maps.Point(13, 46)
        }
      );

      // 드래그가 가능한 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        image: markImage,
        position: mapCenter,
        draggable: true
      });

      this.marker = marker;

      // 로드뷰에 좌표가 바뀌었을 때 발생하는 이벤트를 등록합니다
      kakao.maps.event.addListener(rv, "position_changed", function() {
        // 현재 로드뷰의 위치 좌표를 얻어옵니다
        var rvPosition = rv.getPosition();

        // 지도의 중심을 현재 로드뷰의 위치로 설정합니다
        vue.map.setCenter(rvPosition);

        // 지도 위에 로드뷰 도로 오버레이가 추가된 상태이면
        if (vue.overlayOn) {
          // 마커의 위치를 현재 로드뷰의 위치로 설정합니다
          vue.marker.setPosition(rvPosition);
        }
      });

      // 마커에 dragend 이벤트를 등록합니다
      kakao.maps.event.addListener(marker, "dragend", function() {
        // 현재 마커가 놓인 자리의 좌표입니다
        var position = vue.marker.getPosition();

        // 마커가 놓인 위치를 기준으로 로드뷰를 설정합니다
        vue.toggleRoadview(position);
      });

      //지도에 클릭 이벤트를 등록합니다
      kakao.maps.event.addListener(map, "click", function(mouseEvent) {
        // 지도 위에 로드뷰 도로 오버레이가 추가된 상태가 아니면 클릭이벤트를 무시합니다
        if (!vue.overlayOn) {
          return;
        }

        // 클릭한 위치의 좌표입니다
        var position = mouseEvent.latLng;

        // 마커를 클릭한 위치로 옮깁니다
        vue.marker.setPosition(position);

        // 클락한 위치를 기준으로 로드뷰를 설정합니다
        vue.toggleRoadview(position);
      });

      // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 마커 생성
      new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: this.currentPos // 마커의 위치
      });

      this.map = map;
    },
    toggleRoadview(position) {
      let vue = this;
      this.rvClient.getNearestPanoId(position, 50, function(panoId) {
        // 파노라마 ID가 null 이면 로드뷰를 숨깁니다
        if (panoId === null) {
          vue.toggleMapWrapper(true, position);
        } else {
          vue.toggleMapWrapper(false, position);

          // panoId로 로드뷰를 설정합니다
          vue.rv.setPanoId(panoId, position);
        }
      });
    },
    toggleMapWrapper(active, position) {
      if (active) {
        // 지도를 감싸고 있는 div의 너비가 100%가 되도록 class를 변경합니다
        this.container.className = "";

        // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
        this.map.relayout();

        // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
        this.map.setCenter(position);
      } else {
        // 지도만 보여지고 있는 상태이면 지도의 너비가 50%가 되도록 class를 변경하여
        // 로드뷰가 함께 표시되게 합니다
        if (this.container.className.indexOf("view_roadview") === -1) {
          this.container.className = "view_roadview";

          // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
          this.map.relayout();

          // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
          this.map.setCenter(position);
        }
      }
    },
    toggleOverlay(active) {
      if (active) {
        this.overlayOn = true;

        // 지도 위에 로드뷰 도로 오버레이를 추가합니다
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

        // 지도 위에 마커를 표시합니다
        this.marker.setMap(this.map);

        // 마커의 위치를 지도 중심으로 설정합니다
        this.marker.setPosition(this.map.getCenter());

        // 로드뷰의 위치를 지도 중심으로 설정합니다
        this.toggleRoadview(this.map.getCenter());
      } else {
        this.overlayOn = false;

        // 지도 위의 로드뷰 도로 오버레이를 제거합니다
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

        // 지도 위의 마커를 제거합니다
        this.marker.setMap(null);
      }
    },
    setRoadviewRoad() {
      var control = document.getElementById("roadviewControl");

      // 버튼이 눌린 상태가 아니면
      if (control.className.indexOf("active") === -1) {
        control.className = "active";

        // 로드뷰 도로 오버레이가 보이게 합니다
        this.toggleOverlay(true);
      } else {
        control.className = "";

        // 로드뷰 도로 오버레이를 제거합니다
        this.closeRoadview();
      }
    },
    closeRoadview() {
      var position = this.marker.getPosition();
      this.toggleMapWrapper(true, position);
      // 로드뷰 도로 오버레이를 제거합니다
      this.toggleOverlay(false);
    },

    reposition() {
      console.dir(this.map);
      this.map.panTo(this.currentPos);
    },
    makeChart(data) {
      // 축 범위 지정을 위해 계산좀
      let dmin = 999999999;
      let dmax = 0;
      for (let d of data.data) {
        if (d < dmin) dmin = d;
        if (d > dmax) dmax = d;
      }

      dmin -= 2000;
      dmax += 2000;

      if (dmin < 0) dmin = 0;

      let ctx = $("#chart-area");
      let chartObject = new Chart(ctx, {
        type: "line",
        data: {
          labels: data.labels,
          datasets: [
            {
              data: data.data,
              label: "거래 가격",
              backgroundColor: "#ccebff",
              borderColor: "#0099ff",
              pointBackgroundColor: "#003d66"
            }
          ]
        },
        options: {
          scales: {
            yAxes: [
              {
                ticks: {
                  min: dmin,
                  max: dmax
                }
              }
            ]
          }
        }
      });
      ctx.click(e => {
        let activePoints = chartObject.getElementsAtEvent(e);
        if (activePoints.length > 0) {
          let clickedElementIndex = activePoints[0]._index;

          let no = this.nos[clickedElementIndex];

          this.resetDetail(no);
        }
      });

      dmax = 0; // 보증금 max 값으로 씀
      for (let d of data.deposit) {
        if (d > dmax) dmax = d;
      }
      dmax += 5000;

      dmin = 0; // 월세 max 값으로 씀
      for (let r of data.rent) {
        if (r > dmin) dmin = r;
      }
      if (dmin == 0) dmin = dmax;
      else dmin += 40;

      // 전/월세가 존재하는 경우 그거에 대한 그래프도 따로 보여주기
      let ctx_rent = $("#chart-area-rent");
      let chartObject_rent = new Chart(ctx_rent, {
        type: "line",
        data: {
          labels: data.monthly,
          datasets: [
            {
              label: "월세",
              fill: false,
              borderColor: "rgb(255, 130, 0)",
              data: data.rent,
              yAxisID: "월세"
            },
            {
              data: data.deposit,
              label: "보증금",
              fill: false,
              borderColor: "rgb(255, 210, 0)",
              yAxisID: "보증금"
            }
          ]
        },
        options: {
          scales: {
            yAxes: [
              {
                id: "월세",
                type: "linear",
                position: "left",
                ticks: {
                  max: dmin,
                  min: 0
                }
              },
              {
                id: "보증금",
                type: "linear",
                position: "right",
                ticks: {
                  max: dmax,
                  min: 0
                }
              }
            ]
          }
        }
      });
      ctx_rent.click(e => {
        let activePoints = chartObject_rent.getElementsAtEvent(e);
        if (activePoints.length > 0) {
          let clickedElementIndex = activePoints[0]._index;
          let no = this.nos[clickedElementIndex];
          this.resetDetail(no);
        }
      });
    },
    // 범죄 발생 그래프
    crimeChart(data) {
      let ctx = $("#crime-chart");
      new Chart(ctx, {
        type: "bar",
        data: {
          labels: data.labels,
          datasets: [
            {
              data: data.data,
              label: "해당 구 강력 범죄 발생건수 (2018년)",
              backgroundColor: "#ff1a66"
            }
          ]
        },
        options: {
          responsive: true,
          tooltips: {
            enabled: false
          },
          hover: {
            animationDuration: 0
          },
          animation: {
            onComplete: function() {
              var chartInstance = this.chart,
                ctx = chartInstance.ctx;
              ctx.fillStyle = "black";
              ctx.textAlign = "center";
              ctx.textBaseline = "bottom";

              this.data.datasets.forEach(function(dataset, i) {
                var meta = chartInstance.controller.getDatasetMeta(i);
                meta.data.forEach(function(bar, index) {
                  var data = dataset.data[index];
                  ctx.fillText(data, bar._model.x, bar._model.y - 5);
                });
              });
            }
          }
        }
      });
    },
    resetDetail(no) {
      this.no = no;
      http
        .get("/rest/house/detail/" + no)
        .then(response => {
          this.house = response.data.house;
          this.pyung = (this.house.area*0.3025).toFixed(2);
        })
        .catch(error => {
          alert("Error: ", error);
        });
    }
  }
};
</script>

<style scoped>
.content-box {
  margin-top: 0px;
  text-align: center;
}
.info-box {
  width: 400px;
  margin: auto;
  margin-bottom: 20px;
}
th,
td {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
li {
  list-style: none;
}
#description #title {
  font-size: 13px;
}
.pubDate {
  font-size: 12px;
  text-align: right;
}
.chart-box {
  text-align: center;
}
.chart-canvas {
  margin-top: 20px;
}
.chart-container {
  overflow: auto;
  width: 90%;
}
.right-box {
  padding-left: 2%;
}
.maps {
  width: 600px;
  height: 300px;
}
#news {
  max-height: 300px;
  text-overflow: ellipsis;
  overflow: auto;
}

.news-item > div {
  margin-top: 5px;
}

.bottom-content {
  height: 300px;
}

#map-container {
  overflow: hidden;
  height: 600px;
  position: relative;
}
#mapWrapper {
  width: 100%;
  height: 600px;
  z-index: 1;
}
#rvWrapper {
  width: 100%;
  height: 600px;
  top: 0;
  right: 0;
  position: absolute;
  z-index: 0;
}
#map-container.view_roadview #mapWrapper {
  width: 0%;
}
#roadviewControl {
  position: absolute;
  top: 5px;
  left: 5px;
  width: 42px;
  height: 42px;
  z-index: 1;
  cursor: pointer;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/2018/pc/common/img_search.png)
    0 -450px no-repeat;
}
#roadviewControl.active {
  background-position: 0 -350px;
}

</style>