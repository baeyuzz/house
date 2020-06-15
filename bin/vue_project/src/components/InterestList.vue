<template>
  <div>
    <div class="container mt-3">
      <!-- 관심지역 목록 -->
      <div class="interest-table">
        <div class="table-responsive">
          <table class="table table-hover table-bordered">
            <thead>
              <tr>
                <th colspan="4">관심지역 정보</th>
              </tr>
            </thead>
            <tbody>
              <template v-if="regions.length > 0">
                <tr v-for="(region, index) in regions" v-bind:key="region.no">
                  <td>{{region.province}} {{region.city}} {{region.dong}}</td>
                  <td>
                    <a href="#" @click.prevent="searchEnv(index)">환경 정보 검색</a>
                  </td>
                  <td>
                    <a href="#" @click.prevent="searchShop(index)">동네 업종 검색</a>
                  </td>
                  <td>
                    <a href="#" @click.prevent="removeRegion(region.no)">관심지역에서 제거</a>
                  </td>
                </tr>
              </template>
              <template v-else>
                <tr>
                  <td colspan="4">등록된 관심지역이 없습니다.</td>
                </tr>
              </template>
            </tbody>
          </table>
          <nav id="ir-page" v-if="irPaging.length > 0" v-html="irPaging"></nav>
        </div>
      </div>

      <!-- 관심지역 추가 -->
      <div id="adddiv">
        <router-link class="btn btn-primary" to="/interest/add">관심지역 추가</router-link>
      </div>

      <!-- 검색 리스트 -->
      <div class="information-table mt-3">
        <div class="table-responsive">
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
                <tr v-for="env in envs" v-bind:key="env.no" class="table-row">
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
                  <th>사업체 이름</th>
                  <th>지점명</th>
                  <th>분류1</th>
                  <th>분류2</th>
                  <th>분류3</th>
                  <th>분류4</th>
                  <th>주소</th>
                  <th>경도</th>
                  <th>위도</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="shop in shops" v-bind:key="shop.no">
                  <td>{{shop.shopName}}</td>
                  <td>{{shop.localName}}</td>
                  <td>{{shop.codename1}}</td>
                  <td>{{shop.codename2}}</td>
                  <td>{{shop.codename3}}</td>
                  <td>{{shop.codename4}}</td>
                  <td>{{shop.address}}</td>
                  <td>{{shop.lng}}</td>
                  <td>{{shop.lat}}</td>
                </tr>
              </tbody>
            </template>
          </table>

          <template v-if="envPaging.length > 0">
            <nav id="env-page" v-html="envPaging"></nav>
          </template>
          <template v-if="shopPaging.length > 0">
            <nav id="shop-page" v-html="shopPaging"></nav>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/http-common.js";
import $ from "jquery";

export default {
  data() {
    return {
      regions: [],
      shops: [],
      envs: [],

      irPaging: "",
      envPaging: "",
      shopPaging: "",

      city: "",
      gu: "",
      dong: "",

      curNo: -1
    };
  },
  computed: {
    id() {
      return this.$store.state.id;
    }
  },
  created() {
    this.getRegionList();
  },
  updated() {
    let vue = this;
    $("#ir-page a").on("click", function(e) {
      let n = $(this).attr("id");
      e.preventDefault();

      http
        .post("/rest/interest/pagenav", {
          id: vue.id,
          pg: n
        })
        .then(response => {
          vue.regions = response.data.list;

          if (response.data.hasPaging) {
            vue.irPaging = response.data.paging;
          } else {
            vue.irPaging = "";
          }
        })
        .catch(error => {
          alert("Error: " + error);
        });
    });

    $("#env-page a").on("click", function(e) {
      let n = $(this).attr("id");
      e.preventDefault();

      http
        .post("/rest/env/pagenav", {
          dong: vue.dong,
          pg: n
        })
        .then(response => {
          vue.envs = response.data.list;

          if (response.data.hasPaging) {
            vue.envPaging = response.data.paging;
          } else {
            vue.envPaging = "";
          }
        })
        .catch(error => {
          alert("Error: " + error);
        });
    });

    $("#shop-page a").on("click", function(e) {
      let n = $(this).attr("id");
      e.preventDefault();

      http
        .post("/rest/shop/pagenav", {
          city: vue.city,
          gu: vue.gu,
          dong: vue.dong,
          pg: n
        })
        .then(response => {
          vue.shops = response.data.list;

          if (response.data.hasPaging) {
            vue.shopPaging = response.data.paging;
          } else {
            vue.shopPaging = "";
          }
        })
        .catch(error => {
          alert("Error: " + error);
        });
    });
  },
  methods: {
    searchEnv(index) {
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
    },
    searchShop(index) {
      this.city = this.regions[index].province;
      this.gu = this.regions[index].city;
      this.dong = this.regions[index].dong;
      this.curNo = this.regions[index].no;
      http
        .post("/rest/shop", {
          city: this.city,
          gu: this.gu,
          dong: this.dong
        })
        .then(response => {
          this.shops = response.data.list;

          if (response.data.hasPaging) {
            this.shopPaging = response.data.paging;
          } else {
            this.shopPaging = "";
          }
          this.envs = [];
          this.envPaging = "";
        })
        .catch(error => {
          alert("Error: " + error);
        });
    },
    removeRegion(no) {
      if(no == this.curNo) {
        this.envs = [];
        this.shops = [];
        this.envPaging = "";
        this.shopPaging = "";
      }
      
      http
        .delete("/rest/interest/" + no)
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
          this.regions = response.data.list;

          if (response.data.hasPaging) {
            this.irPaging = response.data.paging;
          }
        })
        .catch(error => {
          alert("Error: " + error);
        });
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
</style>