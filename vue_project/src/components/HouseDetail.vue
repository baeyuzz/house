<template>
  <div class = "container-fluid">
    <br>
    <div class="row">
      <div class="map-news-box col-4 container right-box">
        <div id="map"></div>
        <button class="btn btn-primary" @click="reposition">건물 위치로 이동</button>
        <!-- NEWS -->
        <div style="text-align : left">
          <br>
          <button
            data-toggle="collapse"
            href="#news"
            aria-expanded="false"
            aria-controls="news"
            class="btn btn-warning"
          >관련 뉴스 보기</button>
        </div>
        <div class="collapse" id="news">
          <div style="text-align : left" v-for="(n, index) in news" v-bind:key="n.link">
            <ul v-if="index<4">
              <li>
                <a :title="n.link" v-bind:href="n.link" target="_blank">{{n.title}}</a>
                <div id="description">{{n.description}}</div>
                <div class="pubDate">
                  <span class="pubDate">{{n.pubDate}}</span>
                </div>
              </li>
              <hr />
            </ul>
          </div>
        </div>
      </div>
      <!-- 건물 정보 -->
      <div class="container content-box col-3">
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
            <p>평균 보증금/전세 : {{this.avgRent1}} 원
              <br>
            평균 월세 :   {{this.avgRent2}} 만원</p>
          </div>
        </div>
        <div class="chart-container">
          <canvas class="chart-canvas row" id="crime-chart"></canvas>
        </div>
      </div>
    </div>
    <hr />
    <br />
  </div>
</template>

<script>
import http from "@/http-common.js";
import Chart from "chart.js";
import $ from "jquery";

export default {
  data() {
    return {
      no: 0,
      house: {},

      news: [],

      nos: [],
      avgDeal : '',
      avgRent1 : '',
      avgRent2 : '',

      map: {},
      currentPos: {},
      address: "",
      mapCreated: false,
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
      /*
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      // 주소로 좌표를 검색합니다
      console.log(this.address);
      let vue = this;
      geocoder.addressSearch(this.address, function(
        result,
        status
      ) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          console.log('Kakao Map API - 주소로 좌표 검색 성공');
          vue.setMap(new kakao.maps.LatLng(result[0].y, result[0].x));
        } else {
          console.log('Kakao Map API - 주소로 좌표 검색 실패!!');
          vue.setMap(new kakao.maps.LatLng(33.450705, 126.570677));
        }
      });
      */
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
    },
    reposition() {
      console.dir(this.map);
      this.map.panTo(this.currentPos);
    },
    makeChart(data) {
      // 축 범위 지정을 위해 계산좀
      let dmin = 999999999;
      let dmax = 0;
      for(let d of data.data) {
        if(d < dmin) dmin = d;
        if(d > dmax) dmax = d;
      }

      dmin -= 2000;
      dmax += 2000;

      if(dmin < 0) dmin = 0;

      
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
            yAxes: [{
              ticks: {
                min: dmin,
                max: dmax
              }
            }]
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
      for(let d of data.deposit) {
        if(d > dmax) dmax = d;
      }
      dmax += 2000;

      dmin = 0; // 월세 max 값으로 씀
      for(let r of data.rent){
        if(r>dmin) dmin = r;
      }
      if(dmin == 0)
        dmin = dmax;
      else dmin +=100

      // 전/월세가 존재하는 경우 그거에 대한 그래프도 따로 보여주기
      let ctx_rent = $("#chart-area-rent");
        let chartObject_rent = new Chart(ctx_rent, {
          type: "line",
          data: {
            labels: data.monthly,
            datasets: [
              {
                label: "월세",
                fill : false,
                borderColor: 'rgb(255, 130, 0)',
                data: data.rent,
                yAxisID: '월세',

              },
              {
                data: data.deposit,
                label : "보증금",
                fill : false,
                borderColor: 'rgb(255, 210, 0)',
                yAxisID: '보증금',

              }
          ]
        },
        options: {
          scales: {
            yAxes: [{
              id: '월세',
              type: 'linear',
              position: 'left',
              ticks : {
                max : dmin,
                min : 0,
              }
            }, {
              id: '보증금',
              type: 'linear',
              position: 'right',
              ticks: {
                max: dmax,
                min: 0
              }
            }]
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
              label: "해당 구 강력 범죄 발생횟수 (2018년)",
              backgroundColor: '#ff1a66',
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
#description {
  font-size: 15px;
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
#map {
  width: 500px;
  height: 400px;
}
.right-box{
  padding-left: 5%;
}
</style>