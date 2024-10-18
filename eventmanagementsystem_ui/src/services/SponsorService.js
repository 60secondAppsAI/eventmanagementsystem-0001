import http from "../http-common";

class SponsorService {
  getAllSponsors(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/sponsor/sponsors`, searchDTO);
  }

  get(sponsorId) {
    return this.getRequest(`/sponsor/${sponsorId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/sponsor?field=${matchData}`, null);
  }

  addSponsor(data) {
    return http.post("/sponsor/addSponsor", data);
  }

  update(data) {
  	return http.post("/sponsor/updateSponsor", data);
  }
  
  uploadImage(data,sponsorId) {
  	return http.postForm("/sponsor/uploadImage/"+sponsorId, data);
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

export default new SponsorService();
