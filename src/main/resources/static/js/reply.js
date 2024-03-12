//result 변수는 전체 응답 데이터이고 data는 그 중 일부입니다.
//
// result 변수에는 다음과 같은 정보가 포함될 수 있습니다.
//
// data: 서버에서 전송한 데이터
// status: HTTP 응답 코드
// statusText: HTTP 응답 상태 메시지
// headers: HTTP 응답 헤더
// config: 요청 설정
// request: 요청 객체


// async function get1(bno) {
//
//     const result = await axios.get(`/replies/list/${bno}`)
//
//     //console.log(result)
//
//     return result;
// }


async function getList({bno, page, size, goLast}){

    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})
                                                    //키-값 쌍으로 데이터 엔드포인트에 보냄

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({bno:bno, page:lastPage, size:size})
    }

    return result.data
}

// 여기서 replyObj는 스크립트 코드에서 직접 구성함
// registerBtn.addEventListener부분 보세요

async function addReply(replyObj){
    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}

async function getReply(rno){
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

async function modifyReply(replyObj){
    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data
}

async function removeReply(rno){
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}