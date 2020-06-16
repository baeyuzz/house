<template>
  <div>
    <div id="top-menu" class="page-header">
      <div id="user-box">
        <div v-if="name.length > 0" class="sheader">
          <span id="welcome" class="text-white mr-2">{{name}}님 안녕하세요</span>
          <a href="#" class="mr-2" @click.prevent="mypage">
            <i class="fas fa-user"></i>
            회원정보
          </a>
          <a href="#" class="mr-2" @click.prevent="logout">
            <i class="fas fa-sign-out-alt"></i>
            로그아웃
          </a>
        </div>
        <div v-else class="sheader">
          <router-link to="/user/login">
            <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
            로그인
          </router-link>
          <router-link to="/user/join">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
            가입 하기
          </router-link>
        </div>
      </div>
      <div id="title-box">
        <h1>
          <router-link id="main-title" to="/">
            Happy House
            <i class="fas fa-home"></i>
          </router-link>
        </h1>
      </div>
    </div>
    <section class="main-menu">
      <nav class="navbar navbar-expand-sm bg-secondary navbar-dark mb-0">
        <button
          type="button"
          class="navbar-toggler"
          data-toggle="collapse"
          data-target="#navbarMenu"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarMenu">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link class="nav-link" to="/">홈</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/notice/list">공지사항</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/qna">QnA</router-link>
            </li>

            <li v-if="name.length > 0" class="nav-item">
              <router-link class="nav-link" to="/interest/list">관심지역 보기</router-link>
            </li>

            <!-- DB 테이블에 주소를 좌표로 변환해 넣기 위한 페이지
            <li class="nav-item">
              <router-link class="nav-link" to="/house/addr">asdfasdf</router-link>
            </li>
            -->
          </ul>
        </div>
      </nav>
    </section>
  </div>
</template>

<script>
import http from "@/http-common.js";

export default {
  computed: {
    name() {
      return this.$store.state.name;
    }
  },
  created() {
    console.log("NavHeader Created() Called");
    http
      .get("/rest/user/session")
      .then(response => {
        console.log("/rest/user/session success");
        if(response.data.state) {
          console.log("Session has a data: " + 
                        response.data.name + ", " + 
                        response.data.id);
          this.$store.commit('setname', {name: response.data.name,
                                        id: response.data.id});
        } else {
          console.log("Session don't has a data");
        }
      })
      .catch(error => {
        alert("Error: " + error);
      });
  },
  methods: {
    logout() {
      http
        .get("/rest/user/logout")
        .then(() => {
          this.$store.commit("resetname");
          this.$router.push("/");
        })
        .catch(error => {
          alert("Error: " + error);
        });
    }
    , mypage() {
      this.$router.push("/user/mypage/" + this.$store.state.id);
    }
  }
};
</script>

<style scoped>
#main-title:link {
  color: #3385ff;
  text-decoration: none;
}

#main-title:visited {
  color: #3385ff;
  text-decoration: none;
}

#main-title:hover {
  color: #3385ff;
  text-decoration: none;
}

#title-box > h1 {
  font-family: "Do Hyeon";
  text-align: center;
}

#user-box {
  text-align: right;
}

#title-box {
  align: center;
  padding: 10px;
}

#top-menu {
  margin: 0px;
  background-image: url("/include/headerimg.jpg");
  background-position: center top;
  background-size: cover;
}

#user-box {
  height: 80px;
  font-size: 15px;
}

.sheader {
  padding: 10px;
  margin: 10px;
}

.main-menu {
  padding: 0px;
  text-align: center;
  margin-bottom: 0px;
}
</style>