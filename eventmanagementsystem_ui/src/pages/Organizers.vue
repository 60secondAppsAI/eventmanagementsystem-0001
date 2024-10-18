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
            <organizer-table
            v-if="organizers && organizers.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:organizers="organizers"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-organizers="getAllOrganizers"
             >

            </organizer-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/index";

import OrganizerTable from "@/components/OrganizerTable";
import OrganizerService from "../services/OrganizerService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    OrganizerTable,
  },
  data() {
    return {
      organizers: [],
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
    async getAllOrganizers(sortBy='organizerId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await OrganizerService.getAllOrganizers(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.organizers.length) {
					this.organizers = response.data.organizers;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching organizers:", error);
        }
        
      } catch (error) {
        console.error("Error fetching organizer details:", error);
      }
    },
  },
  mounted() {
    this.getAllOrganizers();
  },
  created() {
    this.$root.$on('searchQueryForOrganizersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllOrganizers();
    })
  }
};
</script>
<style></style>
