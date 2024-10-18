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
            <attendee-table
            v-if="attendees && attendees.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:attendees="attendees"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-attendees="getAllAttendees"
             >

            </attendee-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/index";

import AttendeeTable from "@/components/AttendeeTable";
import AttendeeService from "../services/AttendeeService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    AttendeeTable,
  },
  data() {
    return {
      attendees: [],
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
    async getAllAttendees(sortBy='attendeeId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await AttendeeService.getAllAttendees(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.attendees.length) {
					this.attendees = response.data.attendees;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching attendees:", error);
        }
        
      } catch (error) {
        console.error("Error fetching attendee details:", error);
      }
    },
  },
  mounted() {
    this.getAllAttendees();
  },
  created() {
    this.$root.$on('searchQueryForAttendeesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllAttendees();
    })
  }
};
</script>
<style></style>
