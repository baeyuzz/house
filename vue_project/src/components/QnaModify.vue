<template>
  <div>
    <table>
      <tbody>
        <tr>
          <td>번호</td>
          <td>
            <input type="text" v-model="qna.qna_no" readonly />
          </td>
        </tr>
        <tr>
          <td>제목</td>
          <td>
            <input type="text" v-model="qna.qna_title" />
          </td>
        </tr>
        <tr>
          <td>내용</td>
          <td>
            <input type="text" v-model="qna.qna_content" />
          </td>
        </tr>
        
        <tr>
          <td colspan="2">
            <span class="addContainer" @click="modifyqna">
              수정
            </span>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import Constant from "../Constant";
export default {
  created() {
    let no = this.$route.params.no;
    this.$store.dispatch(Constant.GET_QNA, { no });
    console.log(no);
  },
  computed: {
    qna: {
      get() {
        return this.$store.state.qna;
      },
      set(newqna) {
        console.log("qnaInput computed set...");
        // this.qna.qna_no = newqna.qna_no;
        this.qna.qna_title = newqna.qna_title;
        this.qna.qna_content = newqna.qna_content;
        // this.qna.qna_datetime = newqna.qna_datetime;
      }
    }
  },
  methods: {
  
    modifyqna() {
      if (this.qna.qna_content.trim() != "") {
        console.log("할일 수정:: ");
        this.$store.dispatch(Constant.MODIFY_QNA, { qna: this.qna });
        this.$router.push("/qna");
      } else {
        console.log("공백입력.");
      }
      this.clear();
    },
    clear() {
      this.qna = {}; 
    }
  }
}
</script>

<style scoped>
input:focus {
  outline: none;
}
.inputBox {
  background: white;
  height: 50px;
  line-height: 50px;
  border-radius: 5px;
}
.inputBox input {
  border-style: none;
  font-size: 0.9rem;
}
.addContainer {
  background: linear-gradient(to right, #6478fb, #8763fb);
  display: inline-block;
  width: 3rem;
  border-radius: 0 5px 5px 0;
}

.modifyBtn {
  color: white;
  vertical-align: middle;
}

table {
  margin: 10px auto;
  border-collapse: collapse;
  border: 2px solid #996;
  font: normal 90%/140% verdana, arial, helvetica, sans-serif;
  color: #333;
  background: #fffff0;
}

.caption {
  background-color: #222;
  vertical-align: middle;
  text-align: center;
  padding: 15px;
  font-size: 20px;
  color: #fff;
}

td,
th {
  border: 1px solid #cc9;
  padding: 0.3em;
}
thead th,
tfoot th {
  bgcolor: "#f5deb3";
  border: 1px solid #cc9;
  text-align: center;
  font-size: 1em;
  font-weight: bold;
  color: #444;
  background: #dbd9c0;
}
tbody td a {
  background: transparent;
  color: #72724c;
  text-decoration: none;
  border-bottom: 1px dotted #cc9;
}
tbody td a:hover {
  background: transparent;
  color: #666;
  border-bottom: 1px dotted #72724c;
}
tbody th a {
  background: transparent;
  color: #72724c;
  text-decoration: none;
  font-weight: bold;
  border-bottom: 1px dotted #cc9;
}
tbody th a:hover {
  background: transparent;
  color: #666;
  border-bottom: 1px dotted #72724c;
}
tbody th,
tbody td {
  vertical-align: top;
  text-align: center;
}
tfoot td {
  border: 1px solid #996;
}
.odd {
  color: #333;
  background: #f7f5dc;
}
tbody tr:hover {
  color: #333;
  background: #fff;
}
tbody tr:hover th,
tbody tr.odd:hover th {
  color: #333;
  background: #ddd59b;
}

tbody tr {
  height: 50px;
  line-height: 50px;
}
ul {
  padding: 22px 50px 50px 490px;
  width: 940px;
  list-style: none;
}
#gnb li {
  display: inline;
}
#gnb li a {
  display: inline-block;
  background: #222;
  color: #fff;
  width: 150px;
  height: 23px;
  padding-top: 3px;
  text-align: center;
}
#gnb li a:hover {
  background: #900;
}
</style>