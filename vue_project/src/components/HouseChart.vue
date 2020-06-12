<template>
  <div class="container">
    <canvas id="chart-area"></canvas>
    <canvas id="chart-area-rent"></canvas>
    <a class="btn btn-primary" href="#" @click.prevent="$router.go(-1)">뒤로가기</a>
  </div>
</template>

<script>
import http from "@/http-common.js";
import Chart from "chart.js";
import $ from "jquery";

export default {
  data() {
    return {
      dong: "",
      aptname: "",
      type: "",

      nos: []
    };
  },
  created() {
    this.dong = this.$route.params.dong;
    this.aptname = this.$route.params.aptname;
    this.type = this.$route.params.type;

    http
      .post("/house/chart", {
        dong: this.dong,
        aptname: this.aptname,
        type: this.type
      })
      .then(response => {
        this.nos = response.data.nos;
        this.makeChart(response.data);
      })
      .catch(error => {
        alert("Error: " + error);
      });
  },
  methods: {
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

          this.$router.push("/house/detail/" + no);
        }
      });

      // 월세가 존재하는 경우 그거에 대한 그래프도 따로 보여주기
      if (this.type == "3" || this.type == "4") {
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

            this.$router.push("/house/detail/" + no);
          }
        });
      }
    }
  }
};
</script>

<style scoped>
div {
  text-align: center;
}
a {
  margin-top: 20px;
}
canvas {
  margin-top: 20px;
}
</style>