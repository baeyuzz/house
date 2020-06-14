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
              <label for="dong" class="mr-sm-2">구 이름:</label>
              <input
                v-model="gu"
                type="text"
                class="form-control mr-sm-2"
                name="gu"
                placeholder="구 이름을 입력해주세요"
              />
              <label for="dong" class="mr-sm-2">동 이름:</label>
              <input
                v-model="dong"
                type="text"
                class="form-control mr-sm-2"
                name="dong"
                placeholder="동 이름을 입력해주세요"
              />
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

      gu: "",
      dong: "",
      aptName: "",

      list: [],
      nav: ""
    };
  },
  created() {
    console.log("created called!!");
    this.initialSearch();
  },
  updated() {
    let vue = this;
    $("#house-page a").on("click", function(e) {
      let n = $(this).attr("id");
      e.preventDefault();

      http
        .get("/house/pagenav/" + n)
        .then(response => {
          vue.list = response.data.list;
          vue.nav = response.data.nav;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    });
  },
  methods: {
    goDetail(no) {
      console.log(no);
      this.$router.push("/house/detail/" + no);
    },
    pageMove(page) {
      console.log(page);
      http
        .get("/house/pagenav/" + page)
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
        .get("/house/sort/" + param)
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
        .get("/house/init")
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

      let addr = ('서울특별시 ' + this.gu + ' ' + this.dong)
      if(this.gu.length == 0 || this.dong.length == 0) {
        addr = '';
      }

      http
        .post("/house/list", {
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
</style>