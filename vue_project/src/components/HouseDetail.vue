<template>
  <div>
    <div class="row">
      <div class="map-box col-4">Map 이 들어갈 자리
      <!-- NEWS -->
      <div style="text-align : left">
        <button
          data-toggle="collapse"
          href="#news"
          aria-expanded="false"
          aria-controls="news"
          class="btn"
        >관련 뉴스 보기</button>
      </div>
      <div class="collapse" id="news">
        <div style="text-align : left" v-for="(n) in news" v-bind:key="n.link">
          <ul>
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
    <!-- </div> -->
      <div class="content-box col-3">
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
                <th>동</th>
                <td :title="house.dong">{{house.dong}}</td>
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
                <td :title="dateString">{{dateString}}</td>
              </tr>
            </tbody>
          </table>

          <router-link class="btn btn-info" to="/">메인으로 돌아가기</router-link>
        </div>
      </div>
      <!-- <div class="col-2"></div> -->

      <div class="container chart-box col-4">
        <canvas class="chart-canvas row" id="chart-area"></canvas>
        <canvas class="chart-canvas row" id="chart-area-rent"></canvas>
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
      nos: []
    };
  },
  computed: {
    dateString() {
      let ret = 
        this.house.dealYear + '.' + 
        this.house.dealMonth + '.' + 
        this.house.dealDay;
      return ret;
    }
  },
  created() {
    console.log("Detail created() called!");
    this.no = this.$route.params.no;
    http
      .get("/house/detail/" + this.no)
      .then(response => {
        this.house = response.data.house;
        this.news = response.data.news;

        // house 정보를 잘 가져왔다면 chart 만들기 위한 정보 가져오기
        http
          .post("/house/chart", {
            dong: this.house.dong,
            aptname: this.house.aptName,
            type: this.house.type
          })
          .then(response => {
            this.nos = response.data.nos;
            this.makeChart(response.data);
          })
          .catch(error => {
            alert("Error: " + error);
          });
      })
      .catch(error => {
        alert("Error: ", error);
      });
  },
  methods: {
    newsLink() {
      location.href = this.news.link;
    },
    makeChart(data) {
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

      // 월세가 존재하는 경우 그거에 대한 그래프도 따로 보여주기
      if (this.house.type == "3" || this.house.type == "4") {
        let ctx_rent = $("#chart-area-rent");
        let chartObject_rent = new Chart(ctx_rent, {
          type: "line",
          data: {
            labels: data.labels,
            datasets: [
              {
                data: data.rent,
                label: "월세",
                backgroundColor: "#b3ffb3",
                borderColor: "#00ff00",
                pointBackgroundColor: "#004d00"
              }
            ]
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
      }
    },
    resetDetail(no) {
      this.no = no;
      http
        .get("/house/detail/" + no)
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
</style>