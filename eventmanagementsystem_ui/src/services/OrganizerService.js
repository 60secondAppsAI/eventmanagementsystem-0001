import http from "../http-common";

class OrganizerService {
  getAllOrganizers(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/organizer/organizers`, searchDTO);
  }

  get(organizerId) {
    return this.getRequest(`/organizer/${organizerId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/organizer?field=${matchData}`, null);
  }

  addOrganizer(data) {
    return http.post("/organizer/addOrganizer", data);
  }

  update(data) {
  	return http.post("/organizer/updateOrganizer", data);
  }
  
  uploadImage(data,organizerId) {
  	return http.postForm("/organizer/uploadImage/"+organizerId, data);
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

export default new OrganizerService();
