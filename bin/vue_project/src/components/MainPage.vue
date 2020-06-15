<template>
  <div>
    <section class="search">
      <div class="search-form">
        <form action="#" method="post">
          <div class="form-box">
            <div class="form-group">
              <div class="form-check-inline">
                <label class="form-check-label">
                  아파트 매매
                  <input
                    type="checkbox"
                    v-model="aptdeal"
                    class="form-check-input"
                    name="aptdeal"
                  />
                </label>
              </div>
              <div class="form-check-inline">
                <label class="form-check-label">
                  연립,다세대 매매
                  <input
                    type="checkbox"
                    v-model="housedeal"
                    class="form-check-input"
                    name="housedeal"
                  />
                </label>
              </div>
              <div class="form-check-inline">
                <label class="form-check-label">
                  아파트 전월세
                  <input
                    type="checkbox"
                    v-model="aptrent"
                    class="form-check-input"
                    name="aptrent"
                  />
                </label>
              </div>
              <div class="form-check-inline">
                <label class="form-check-label">
                  연립,다세대 전월세
                  <input
                    type="checkbox"
                    v-model="houserent"
                    class="form-check-input"
                    name="houserent"
                  />
                </label>
              </div>
            </div>
            <div class="form-group form-inline sms-form-center">
              <label for="sido" class="mr-sm-2">시/도</label>
              <select v-model="sido" class="form-control mr-sm-2" name="sido" id="sido">
                <option v-for="sido in sidoList" :key="sido" :value="sido">{{sido}}</option>
              </select>

              <label for="sigungu" class="mr-sm-2">시/군/구</label>
              <select v-model="sigungu" class="form-control mr-sm-2" name="sigungu" id="sigungu">
                <option v-for="sigungu in sigunguList" :key="sigungu" :value="sigungu">{{sigungu}}</option>
              </select>

              <label for="dong" class="mr-sm-2">동</label>
              <select v-model="dong" class="form-control mr-sm-2" name="dong" id="dong">
                <option v-for="dong in dongList" :key="dong" :value="dong">{{dong}}</option>
              </select>

              <label for="aptName" class="mr-sm-2">아파트 이름:</label>
              <input
                v-model="aptName"
                type="text"
                class="form-control mr-sm-2"
                name="aptName"
                placeholder="아파트 이름을 입력해주세요"
              />
              <button @click.prevent="search" class="btn btn-primary">검색</button>
            </div>
          </div>
        </form>
      </div>
    </section>
    <section class="container">
      <div id="house-map"></div>
    </section>
    <section class="information">
      <table class="table table-hover table-bordered">
        <thead>
          <tr id="head-row">
            <th @click="clickSort('no');" title="번호">
              <i class="fas fa-angle-down"></i>번호
            </th>
            <th @click="clickSort('address');" title="주소">
              <i class="fas fa-angle-down"></i>주소
            </th>
            <th @click="clickSort('aptName');" title="아파트 이름">
              <i class="fas fa-angle-down"></i>아파트 이름
            </th>
            <th @click="clickSort('buildYear');" title="건축 년도">
              <i class="fas fa-angle-down"></i>건축 년도
            </th>
            <th @click="clickSort('area');" title="면적">
              <i class="fas fa-angle-down"></i>면적
            </th>
            <th @click="clickSort('floor');" title="층수">
              <i class="fas fa-angle-down"></i>층수
            </th>
            <th @click="clickSort('dealDate');" title="거래일">
              <i class="fas fa-angle-down"></i>거래일
            </th>
            <th @click="clickSort('dealAmount');" title="거래 금액(보증금)">
              <i class="fas fa-angle-down"></i>거래 금액(보증금)
            </th>
            <th @click="clickSort('rentMoney');" title="월세">
              <i class="fas fa-angle-down"></i>월세
            </th>
            <th @click="clickSort('type');" title="거래 유형">
              <i class="fas fa-angle-down"></i>거래 유형
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="deal in list" v-bind:key="deal.no" @click="goDetail(deal.no)">
            <td :title="deal.no">{{deal.no}}</td>
            <td :title="deal.address">{{deal.address}}</td>
            <td :title="deal.aptName">{{deal.aptName}}</td>
            <td :title="deal.buildYear">{{deal.buildYear}}</td>
            <td :title="deal.area">{{deal.area}}</td>
            <td :title="deal.floor">{{deal.floor}}</td>
            <td :title="deal.dealDate">{{deal.dealDate}}</td>
            <td :title="deal.dealAmount">{{deal.dealAmount}}</td>
            <td :title="deal.rentMoney">{{deal.rentMoney}}</td>
            <td :title="deal.typeString">{{deal.typeString}}</td>
          </tr>
        </tbody>
      </table>

      <!-- 페이징 처리 부분 -->
      <nav id="house-page" v-html="nav"></nav>
    </section>
  </div>
</template>

<script>
import http from "@/http-common.js";
import $ from "jquery";

export default {
  data() {
    return {
      aptdeal: true,
      housedeal: true,
      aptrent: true,
      houserent: true,

      sido: "",
      sigungu: "",
      dong: "",
      aptName: "",

      sidoList: [],
      sigunguList: [],
      dongList: [],

      list: [],
      nav: "",

      map: {},
      mapCreated: false
    };
  },
  watch: {
    list() {
      console.log("init map in list watcher called!");
      this.initMap();
      this.mapCreated = true;
    },
    sido() {
      this.setGugunName();
    },
    sigungu() {
      this.setDongName();
    }
  },
  // Created : 처음 데이터들을 불러오기 위함
  created() {
    console.log("created called!!");
    this.initialSearch();

    // 검색 옵션 설정을 위해 시/도 이름들 가져오기
    http
      .get("/rest/house/sidoname")
      .then(response => {
        this.sidoList = response.data;
        this.sido = this.sidoList[0];
      })
      .catch(error => {
        alert("Error: " + error);
      });
  },
  // Updated : 페이징 요소에 클릭 이벤트를 설정하기 위함
  updated() {
    let vue = this;
    $("#house-page a").on("click", function(e) {
      let n = $(this).attr("id");
      e.preventDefault();

      http
        .get("/rest/house/pagenav/" + n)
        .then(response => {
          vue.list = response.data.list;
          vue.nav = response.data.nav;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    });
  },
  // Mounted : 카카오 맵 API 를 사용하기 위함
  mounted() {
    if (window.kakao && window.kakao.maps) {
      if (!this.mapCreated && this.list.length > 0) {
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
    initMap() {
      // 지도를 담을 컨테이너를 가져옵니다
      var container = document.getElementById("house-map");
      // 지도에 옵션을 설정합니다
      var options = { level: 3 };
      if (this.list.length > 0) {
        options.center = new kakao.maps.LatLng(
          this.list[0].lat,
          this.list[0].lng
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

      for (let deal of this.list) {
        let obj = {};
        obj.latlng = new kakao.maps.LatLng(deal.lat, deal.lng);
        obj.content = "<div>" + deal.aptName + "</div>";

        positions.push(obj);
      }

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
        console.dir(infowindow);
      };
    },
    goDetail(no) {
      console.log(no);
      this.$router.push("/house/detail/" + no);
    },
    pageMove(page) {
      console.log(page);
      http
        .get("/rest/house/pagenav/" + page)
        .then(response => {
          this.list = response.data.list;
          this.nav = response.data.nav;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    clickSort(param) {
      console.log(param);
      http
        .get("/rest/house/sort/" + param)
        .then(response => {
          this.list = response.data.list;
          this.nav = response.data.nav;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    initialSearch() {
      console.log("initialSearch() called!!");
      http
        .get("/rest/house/init")
        .then(response => {
          this.list = response.data.list;
          this.nav = response.data.nav;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    search() {
      console.log("search() called!!");

      // 아무것도 선택 안하면 검색 안됨!
      if (
        !this.aptdeal &&
        !this.housedeal &&
        !this.aptrent &&
        !this.houserent
      ) {
        alert("최소한 하나의 타입은 선택해야합니다.");
        return;
      }

      if(this.sido != "서울특별시") {
        alert("현재는 서울 특별시의 정보만 제공됩니다!");
        return;
      }

      let addr = this.sido + ' ' + this.sigungu + ' ' + this.dong;

      http
        .post("/rest/house/list", {
          aptdeal: this.aptdeal,
          housedeal: this.housedeal,
          aptrent: this.aptrent,
          houserent: this.houserent,

          address: addr,
          aptName: this.aptName
        })
        .then(response => {
          this.list = response.data.list;
          this.nav = response.data.nav;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    setGugunName() {
      http
        .get("/rest/house/gugunname/" + this.sido)
        .then(response => {
          this.sigunguList = response.data;
          this.sigungu = this.sigunguList[0];
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    setDongName() {
      http
        .get("/rest/house/dongname/" + this.sigungu)
        .then(response => {
          this.dongList = response.data;
          this.dong = this.dongList[0];
        })
        .catch(error => {
          alert("Error: " + error);
        });
    }
  }
};
</script>
<style scoped>
.search-form {
  text-align: center;
  padding: 10px;
}

.sms-form-center {
  justify-content: center;
}

.information {
  margin-left: 20px;
  margin-right: 20px;
}

.table {
  margin-top: 20px;
}

th {
  background: skyblue;
  cursor: pointer;
}

th,
td {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

th > i {
  visibility: hidden;
}

.pagination a {
  display: inline-block;
  cursor: pointer;
}

.nav {
  display: inline-block;
}

#nav-container {
  text-align: center;
}

#house-map {
  widows: 1000px;
  height: 800px;
}
</style>