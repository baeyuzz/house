<template>
  <div>
    <div class="content-box">
      <div class="image-box">
        <img class="img-thumbnail" :alt="house.img" :src="'/happyhouse/img/' + house.img" />
      </div>
      <div class="info-box table-responsive">
        <table class="table">
          <thead>
            <tr>
              <td colspan="6">
                <h3>
                  <i class="fas fa-building"></i>건물 정보
                </h3>
              </td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th>동</th>
              <th>아파트 이름</th>
              <th>건축 년도</th>
              <th>면적</th>
              <th>층</th>
              <th v-if="house.type > 2">임대료</th>
            </tr>
            <tr>
              <td>{{house.dong}}</td>
              <td>{{house.aptName}}</td>
              <td>{{house.buildYear}}</td>
              <td>{{house.area}}</td>
              <td>{{house.floor}}</td>
              <td v-if="house.type > 2">{{house.rentMoney}}</td>
            </tr>
          </tbody>
        </table>
        <table class="table">
          <thead>
            <tr>
              <td colspan="6">
                <h3>
                  <i class="fas fa-barcode"></i>거래 정보
                </h3>
              </td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th>거래 유형</th>
              <th>거래 금액</th>
              <th>거래 날짜</th>
            </tr>
            <tr>
              <td>{{house.typeString}}</td>
              <td>{{house.dealAmount}}</td>
              <td>{{house.dealYear}}.{{house.dealMonth}}.{{house.dealDay}}</td>
            </tr>
          </tbody>
        </table>
        <router-link class="btn btn-info" to="/">메인으로 돌아가기</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/http-common.js";

export default {
  data() {
    return {
      no: 0,
      house: {}
    };
  },
  created() {
    console.log('Detail created() called!');
    this.no = this.$route.params.no;
    http
      .get("/house/detail/" + this.no)
      .then(response => {
        this.house = response.data;
      })
      .catch(error => {
        alert("Error: ", error);
      });
  }
};
</script>

<style scoped>
.content-box {
  margin-top: 0px;
  text-align: center;
}
.image-box {
  width: 700px;
  margin: auto;
  margin-top: 10px;
}
.image-box img {
  width: 700px;
  height: 400px;
}
.info-box {
  width: 700px;
  margin: auto;
  margin-bottom: 20px;
}
</style>