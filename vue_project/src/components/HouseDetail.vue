<template>

<div>
	<div class="content-box">
		<div class="image-box">
			<img class="img-thumbnail" :alt="house.img"
				:src="'/happyhouse/img/' + house.img" />
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

      <hr>
      <br>
			<!-- 여기에 뉴스 넣기 -->
			<div>
				<button data-toggle="collapse" href="#news"
					aria-expanded="false" aria-controls="news" class = "btn">
          관련 뉴스 보기</button>
			</div>
			<div class="collapse" id="news">
				<div v-for="(n) in news" v-bind:key="n.link">
          <ul>
            <li><a :title="n.title" v-bind:href="n.link" target="_blank"> {{n.title}}</a></li>
            <li>{{n.description}}</li>
            <li>{{n.pubDate}}</li>
          </ul>
				</div>
			</div>

      <br>
      <hr>

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
      house: {},
      news : [],
    };
  },
  created() {
    console.log('Detail created() called!');
    this.no = this.$route.params.no;
    http
      .get("/house/detail/" + this.no)
      .then(response => {
        this.house = response.data.house;
        this.news = response.data.news;
        console.log(this.house);
        console.log(this.news.length);
      })
      .catch(error => {
        alert("Error: ", error);
      });
  },
  methods: {
    newsLink(){
      location.href = this.news.link;
    }
  },
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
li {
  list-style: none;
}
</style>