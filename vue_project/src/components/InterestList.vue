<template>
  <div>
    <div class="mt-3 row">
      <!-- 관심지역 목록 -->
      <div class="interest-table col-4">
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
                      <div style="float : left">
                        {{region.sigungu}}
                      </div>
                      <div style="text-align : right">
                        <a href="#" @click.prevent="searchEnv(index)">환경 <i class="fa fa-search" aria-hidden="true"></i></a>
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        <a href="#" @click.prevent="searchShop(index)">상권 <i class="fa fa-search" aria-hidden="true"></i></a>
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        <a href="#" @click.prevent="removeRegion(index)"><i class="fa fa-trash" aria-hidden="true"></i></a>
                      </div>  
                    </td>
                  <!-- </tr>
                  <tr :key="region.no">
                    
                  </tr>
                  <tr :key="region.no">
                  </tr>
                  <tr :key="region.no">
                  -->
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
        <!-- 관심지역 추가 -->
        <div id="adddiv">
          <router-link class="btn btn-primary" to="/interest/add">관심지역 추가</router-link>
        </div>
      </div>

      <!-- 환경, 상권정보 -->
      <div class="information-table mt-3 col-8">
        <div id="map"></div>
        <div class="container">
          <div class="table-responsive info-table">
            <table class="table table-hover table-bordered">
              <template v-if="envs.length > 0">
                <thead>
                  <tr>
                    <th>사업체 이름</th>
                    <th>환경 영향 구분 코드</th>
                    <th>주소</th>
                    <th>경도</th>
                    <th>위도</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="env in envs" v-bind:key="env.no">
                    <td>{{env.name}}</td>
                    <td>{{env.bizcode}}</td>
                    <td>{{env.address}}</td>
                    <td>{{env.lng}}</td>
                    <td>{{env.lat}}</td>
                  </tr>
                </tbody>
              </template>
              <template v-if="shops.length > 0">
                <thead>
                  <tr>
                    <th @click="sortShop('name')">사업체 이름</th>
                    <th @click="sortShop('type4')">업종 분류</th>
                    <th @click="sortShop('address')">주소</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(shop, index) in shops" v-bind:key="shop.no" @click="clickShop(index)">
                    <td :title="shop.name">{{shop.name}}</td>
                    <td :title="shop.type4">{{shop.type4}}</td>
                    <td :title="shop.address">{{shop.address}}</td>
                  </tr>
                </tbody>
              </template>
            </table>
          </div>
        </div>
        <div class="container">
          <canvas id="chart"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/http-common.js";
import Chart from "chart.js";
import $ from 'jquery';

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
      mapCreate: 0
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
  methods: {
    searchEnv(index) {
      console.log(index);
      /*
      this.dong = this.regions[index].dong;
      this.curNo = this.regions[index].no;
      http
        .post("/rest/env", {
          dong: this.dong
        })
        .then(response => {
          this.envs = response.data.list;

          if (response.data.hasPaging) {
            this.envPaging = response.data.paging;
          } else {
            this.envPaging = "";
          }
          this.shops = [];
          this.shopPaging = "";
        })
        .catch(error => {
          alert("Error: " + error);
        });
      */
    },
    searchShop(index) {
      this.curIdx = index;

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
          this.makeChart(response.data.chart)
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    makeChart(chartData) {
      // 라벨, 데이터 만들기
      let labels = [];
      let data = [];
      for(let d of chartData) {
        labels.push(d.type);
        data.push(d.count);
      }

      let ctx = $("#chart");
      new Chart(ctx, {
        type: "radar",
        data: {
          labels: labels,
          datasets: [
            {
              data: data,
              label: "상권 분포",
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
    removeRegion(index) {
      if (index == this.curIdx) {
        this.shops = [];
        this.envs = [];
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
      if(this.mapList.length == 0) return;

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

      let positions = [];

      // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
      // 지도에 모든 Marker 가 보이게 하기 위함
      var bounds = new kakao.maps.LatLngBounds();

      for (let shop of this.mapList) {
        let obj = {};
        obj.latlng = new kakao.maps.LatLng(shop.lat, shop.lng);
        obj.content = 
            '<div class="kakao-wrap">' + 
            '    <input id="kakao-no" type="hidden" value=' + shop.no + '/>' + 
            '    <div class="kakao-info">' + 
            '        <div class="kakao-title">' + 
                          shop.name + 
            '        </div>' + 
            '        <div class="kakao-body">' + 
            '            <div class="kakao-desc">' + 
            '                <div class="kakao-ellipsis">' + shop.address + '</div>' + 
            '            </div>' + 
            '        </div>' + 
            '    </div>' +    
            '</div>';
        obj.trickVal = shop.no;

        positions.push(obj);
        bounds.extend(obj.latlng);
      }

      // 모든 마커가 보이게 지도 중심과 범위를 재 설정합니다
      map.setBounds(bounds);

      for (let i = 0; i < positions.length; i++) {
        // 마커를 생성합니다
        let marker = new kakao.maps.Marker({
          map: map, // 마커를 표시할 지도
          position: positions[i].latlng // 마커의 위치
        });

        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: positions[i].content // 인포윈도우에 표시할 내용
        });
        infowindow.trickVal = positions[i].trickVal;

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
        kakao.maps.event.addListener(
          marker,
          "click",
          this.makeClickListener(infowindow)
        );
      }
      console.log("Add all marker complete");
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
}

th,
td {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

#map {
  height: 400px;
}
</style>