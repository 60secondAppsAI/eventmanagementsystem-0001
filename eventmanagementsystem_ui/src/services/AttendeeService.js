import http from "../http-common";

class AttendeeService {
  getAllAttendees(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/attendee/attendees`, searchDTO);
  }

  get(attendeeId) {
    return this.getRequest(`/attendee/${attendeeId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/attendee?field=${matchData}`, null);
  }

  addAttendee(data) {
    return http.post("/attendee/addAttendee", data);
  }

  update(data) {
  	return http.post("/attendee/updateAttendee", data);
  }
  
  uploadImage(data,attendeeId) {
  	return http.postForm("/attendee/uploadImage/"+attendeeId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new AttendeeService();
