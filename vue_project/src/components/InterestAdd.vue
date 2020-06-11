<template>
  <div>
    <div class="container mt-3">
      <h3>관심지역 추가</h3>

      <form action="#">
        <!-- 도/광역시 -->
        <div class="form-group form-inline">
          <label for="province" class="col-sm-3 offset-3 control-label">도/광역시</label>
          <div class>
            <input
              v-model="province"
              ref="province"
              type="text"
              class="form-control"
              name="province"
            />
          </div>
        </div>

        <!-- 시/군/구 -->
        <div class="form-group form-inline">
          <label for="city" class="col-sm-3 offset-3 control-label">시/군/구</label>
          <div class>
            <input v-model="city" ref="city" type="text" class="form-control" name="city" />
          </div>
        </div>

        <!-- 도/광역시 -->
        <div class="form-group form-inline">
          <label for="dong" class="col-sm-3 offset-3 control-label">읍/면/동</label>
          <div class>
            <input v-model="dong" ref="dong" type="text" class="form-control" name="dong" />
          </div>
        </div>

        <div class="form-group" id="adddiv">
          <button @click.prevent="addRegion" class="btn button pr-2 pl-2" id="add">추가</button>
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
      province: "",
      city: "",
      dong: ""
    };
  },
  computed: {
    id() {
      return this.$store.state.id;
    }
  },
  methods: {
    addRegion() {
      if (this.province.length == 0) {
        alert("도/광역시를 입력해 주세요");
        this.$refs.province.focus();
        return;
      }
      if (this.city.length == 0) {
        alert("시/군/구를 입력해 주세요");
        this.$refs.city.focus();
        return;
      }
      if (this.dong.length == 0) {
        alert("읍/면/동 을 입력해 주세요");
        this.$refs.dong.focus();
        return;
      }

      http
        .post("/interest/add", {
          province: this.province,
          city: this.city,
          dong: this.dong,
          id: this.id
        })
        .then(response => {
          if (response.data) {
            alert("관심지역이 추가되었습니다");
          } else {
            alert("관심지역 추가 실패");
          }

          this.$router.push("/interest/list");
        })
        .catch(error => {
          alert("Error: " + error);
          this.$router.push("/interest/list");
        });
    }
  }
};
</script>

<style scoped>
.button {
  border: none;
  color: #fff;
  padding: 15px 0;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 15px;
  margin: 4px;
  cursor: pointer;
}

#add {
  background-color: #8fc242; /*초록색*/
  text-align: center;
}
#adddiv {
  text-align: center;
}

h3 {
  text-align: center;
}
</style>