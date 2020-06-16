<template>
  <div class="container-fluid">
    <br />
    <div class="row">
      <div class="map-news-box col-4 container right-box">
        <div class="maps">
          <div id="mapWrapper" style="width:400px;height:100%;float:left">
            <div id="map" style="width:100%;height:100%"></div>
          </div>
          <div id="roadWrap" style="width:400px;height:300px;float:left">
            <div id="roadview" style="width:100%;height:100%"></div>
            <!-- 로드뷰를 표시할 div 입니다 -->
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
                    <i class="fas fa-building"></i>건물 정보
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
                <td :title="house.buildYear">{{house.buildYear}}</td>
              </tr>
              <tr>
                <th>면적</th>
                <td :title="house.area">{{house.area}}</td>
              </tr>
              <tr>
                <th>층</th>
                <td :title="house.floor">{{house.floor}}</td>
              </tr>
              <tr>
                <th>거래 유형</th>
                <td :title="house.typeString">{{house.typeString}}</td>
              </tr>
              <tr>
                <th>거래 금액</th>
                <td :title="house.dealAmount">{{house.dealAmount}}</td>
              </tr>
              <tr v-if="house.type > 2">
                <th>임대료</th>
                <td :title="house.rentMoney">{{house.rentMoney}}</td>
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
            <span>평균 매매가 : {{this.avgDeal}} 천원</span>
          </div>
        </div>
        <div v-if="this.avgRent1>-1" class="chart-container">
          <canvas class="chart-canvas row" id="chart-area-rent"></canvas>
          <div style="font-size:14px">
            <p>
              평균 보증금/전세 : {{this.avgRent1}} 원
              <br />
              평균 월세 : {{this.avgRent2}} 만원
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
          <div>관련 뉴스 보기</div> 
          <br>
          <!-- <table class="table table-bordered">
            <tbody>
            <tr>
              <td v-for="(n) in news" v-bind:key="n.link"  class="news-item">
                <div>
                  <a :title="n.title" v-bind:href="n.link" target="_blank">{{n.title}}</a>
                </div>
                <div :title="n.description" id="description">{{n.description}}</div>
                <div class="pubDate">
                  <span class="pubDate">{{n.pubDate}}</span>
                </div>
              </td>
            </tr>
            </tbody>
          </table> -->
          <div v-for="(n,index) in this.news" :key="n.no">
            <div v-if="index<3" style="float : left; font-size : 14px;">
              <a :title="n.title" v-bind:href="n.link" target="_blank">{{n.title}}</a>
              <div :title="n.description" >{{n.description}}</div>
              <div class="pubDate">
                  <span class="pubDate">{{n.pubDate}}</span>
                </div>
            </div>
                <br>
          </div>
        </div>
      </div>
      <div class="chart-container col-4">
        <canvas class="chart-canvas" id="crime-chart"></canvas>
      </div>
      <div class="col-1"/>
    </div>
  </div>
</template>

<script>
import http from "@/http-common.js";
import Chart from "chart.js";
import $ from "jquery";
import Vue from 'vue';
import VueCarousel from 'vue-carousel';
Vue.use(VueCarousel);

export default {
  data() {
    return {
      no: 0,
      house: {},

      news: [],

      nos: [],
      avgDeal: "",
      avgRent1: "",
      avgRent2: "",

      map: {},
      currentPos: {},
      address: "",
      mapCreated: false
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
      this.currentPos = coords;

      // 지도를 담을 컨테이너를 가져옵니다
      var container = document.getElementById("map");

      // 지도에 옵션을 설정합니다
      var options = {
        // 처음 표시될 좌표
        center: coords,
        // 처음 표시될 확대/축소 레벨
        level: 3
      };

      // 지도를 만들어 컨테이너에 붙입니다.
      var map = new kakao.maps.Map(container, options);
      map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW); //지도 위에 로드뷰 도로 올리기
      console.log("Make and attach map Complete");

      // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
      console.log("Add control to map Complete");

      // 마커 생성
      new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: this.currentPos // 마커의 위치
      });
      console.log("Add marker to map Complete");

      console.dir(map);
      this.map = map;
      /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      let rvContainer = document.getElementById("roadview"); //로드뷰를 표시할 div
      let rv = new kakao.maps.Roadview(rvContainer); //로드뷰 객체
      let rvClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
      let mapWrapper = document.getElementById("mapWrapper");

      toggleRoadview(coords);

      let markImage = new kakao.maps.MarkerImage(
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

      let rvMarker = new kakao.maps.Marker({
        image: markImage,
        position: coords,
        draggable: true,
        map: map
      });

      kakao.maps.event.addListener(map, "click", function(mouseEvent) {
        // 현재 클릭한 부분의 좌표를 리턴
        var position = mouseEvent.latLng;

        rvMarker.setPosition(position);
        toggleRoadview(position); //로드뷰를 토글합니다
      });

      function toggleRoadview(position) {
        //전달받은 좌표(position)에 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄웁니다
        rvClient.getNearestPanoId(position, 50, function(panoId) {
          if (panoId === null) {
            rvContainer.style.display = "none"; //로드뷰를 넣은 컨테이너를 숨깁니다
            mapWrapper.style.heigth = "100%";
            map.relayout();
          } else {
            // mapWrapper.style.height = '50%';
            map.relayout(); //지도를 감싸고 있는 영역이 변경됨에 따라, 지도를 재배열합니다
            rvContainer.style.display = "block"; //로드뷰를 넣은 컨테이너를 보이게합니다
            rv.setPanoId(panoId, position); //panoId를 통한 로드뷰 실행
            rv.relayout(); //로드뷰를 감싸고 있는 영역이 변경됨에 따라, 로드뷰를 재배열합니다
          }
        });
      }

      /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
      dmax += 2000;

      dmin = 0; // 월세 max 값으로 씀
      for (let r of data.rent) {
        if (r > dmin) dmin = r;
      }
      if (dmin == 0) dmin = dmax;
      else dmin += 100;

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
  padding-left: 5%;
}
.maps {
  width: 500;
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

.bottom-content{
  height: 300px;
}

</style>