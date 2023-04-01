<template>
    <div class="NotFound">
        <div class="main">
            <p style='text-align:center;'>
                <img src='../assets/pageNotFound.png' />
            </p>
            <div class="msg4">很抱歉，您访问的页面不存在！</div>
            <div class="msg4">请仔细检查您输入的网址是否正确。</div>
            <a href='/#/' class="return-home">
                返回首页</a>
        </div>
    </div>
</template>
<script>
const options = {
    data() {
        return {

        };
    },
    mounted() {
        this.$nextTick(() => {
            document.title = '页面走丢了';
        });
    },
}
export default options;
</script>
<style>
.main{
    margin-top: 12%;
}
.NotFound {
    width: 100%;
    height: 100vh;
    margin: auto;
    background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
    position: absolute;
    height: 100%;
}

.msg4 {
    text-align: center;
    font-size: 20px;
    line-height: 30px;
    color: #333333;
}

.return-home {
    text-align: center;
    color: #333;
    font-size: 18px;
    text-decoration: none;
    width: 100px;
    height: 32px;
    line-height: 32px;
    border: 3px solid #f8f7f8;
    display: block;
    margin: 30px auto;
    border-radius: 10px;
}
</style>
