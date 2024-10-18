<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <sponsor-table
            v-if="sponsors && sponsors.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:sponsors="sponsors"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-sponsors="getAllSponsors"
             >

            </sponsor-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/index";

import SponsorTable from "@/components/SponsorTable";
import SponsorService from "../services/SponsorService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    SponsorTable,
  },
  data() {
    return {
      sponsors: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllSponsors(sortBy='sponsorId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await SponsorService.getAllSponsors(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.sponsors.length) {
					this.sponsors = response.data.sponsors;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching sponsors:", error);
        }
        
      } catch (error) {
        console.error("Error fetching sponsor details:", error);
      }
    },
  },
  mounted() {
    this.getAllSponsors();
  },
  created() {
    this.$root.$on('searchQueryForSponsorsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllSponsors();
    })
  }
};
</script>
<style></style>
