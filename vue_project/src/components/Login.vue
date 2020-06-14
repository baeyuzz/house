<template>
  <div>
    <div class="container mt-3">
      <h4>로그인</h4>
      {{errorMessage}}
      <form class="form-horizontal" method="post" action="#">
        <!-- ID -->
        <div class="form-group form-inline">
          <label class="col-sm-2 offset-4 control-label">아이디</label>
          <div class="mr-2">
            <input v-model="userid" ref="userid" type="text" class="form-control" id="userid" name="userid"/>
          </div>
          <input type="checkbox" id="idsave" name="idsave" value="saveok"/>아이디 저장
          <br />
        </div>

        <!-- 비밀번호 -->
        <div class="form-group form-inline">
          <label class="col-sm-2 offset-4 control-label">비밀번호</label>
          <div class="mr-2">
            <input v-model="userpwd" ref="userpwd" type="password" class="form-control" id="userpwd" name="userpwd" />
          </div>
          <button @click.prevent="login" class="button btn" id="login">로그인</button>
        </div>

        <div id="btns">
          <br />
          <router-link to="/user/findpw">
            <input class="button btn pl-2 pr-2" id="findpwd" type="button" value="비밀번호 찾기" />
          </router-link>
          <router-link to="/">
            <input class="button btn pl-2 pr-2" type="button" id="gohome" value="돌아가기" />
          </router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import http from "@/http-common.js";

export default {
    data() {
        return {
            errorMessage: ''
            , userid: ''
            , userpwd: ''
        }
    },
    methods: {
        login() {
            if(this.userid.length == 0) {
                alert('아이디를 입력하세요');
                this.$refs.userid.focus();
                return;
            }
            if(this.userpwd.length == 0) {
                alert('비밀번호를 입력하세요');
                this.$refs.userpwd.focus();
                return;
            }

            http.post('/rest/user/login', {
                userid: this.userid
                , userpwd: this.userpwd
            })
            .then((response) => {
                if(response.data.state) {
                    this.$store.commit('setname', {name: response.data.name,
                                                   id: this.userid});
                    this.$router.push('/');
                } else {
                    this.errorMessage = "아이디 또는 비밀번호가 잘못되었습니다";
                }
            })
            .catch((error) => {
                alert("Error: ", error);
            });
        }
    },
};
</script>

<style scoped>
#btns {
  text-align: center;
}
.button {
  border: none;
  color: #fff;
  padding: 15px 0;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 15px;
  cursor: pointer;
}

#gohome {
  background-color: #99adc2; /*약간 회색*/
}

#findpwd {
  background-color: #ffc266; /*노란색*/
}
#login {
  background-color: #8fc242; /*초록색*/
}
h4 {
  text-align: center;
}
</style>