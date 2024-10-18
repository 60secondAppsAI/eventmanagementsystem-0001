import Vue from 'vue'
import VueRouter from 'vue-router'
import Events from  '@/pages/Events.vue';
import EventDetail from  '@/pages/EventDetail.vue';
import Attendees from  '@/pages/Attendees.vue';
import AttendeeDetail from  '@/pages/AttendeeDetail.vue';
import Organizers from  '@/pages/Organizers.vue';
import OrganizerDetail from  '@/pages/OrganizerDetail.vue';
import Sessions from  '@/pages/Sessions.vue';
import SessionDetail from  '@/pages/SessionDetail.vue';
import Tickets from  '@/pages/Tickets.vue';
import TicketDetail from  '@/pages/TicketDetail.vue';
import Sponsors from  '@/pages/Sponsors.vue';
import SponsorDetail from  '@/pages/SponsorDetail.vue';
import Feedbacks from  '@/pages/Feedbacks.vue';
import FeedbackDetail from  '@/pages/FeedbackDetail.vue';

Vue.use(VueRouter)

let routes = [
	{
		// will match everything
		path: '*',
		component: () => import('../views/404.vue'),
	},
	{
		path: '/',
		name: 'Home',
			redirect: '/events',
								},
	{
		path: '/dashboard',
		name: 'Dashboard',
		layout: "dashboard",
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () => import(/* webpackChunkName: "dashboard" */ '../views/Dashboard.vue'),
	},
	{
		path: '/layout',
		name: 'Layout',
		layout: "dashboard",
		component: () => import('../views/Layout.vue'),
	},
	{
		path: '/events',
		name: 'Events',
		layout: "dashboard",
		component: Events,
	},
	{
	    path: '/event/:eventId', 
	    name: 'EventDetail',
		layout: "dashboard",
	    component: EventDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/attendees',
		name: 'Attendees',
		layout: "dashboard",
		component: Attendees,
	},
	{
	    path: '/attendee/:attendeeId', 
	    name: 'AttendeeDetail',
		layout: "dashboard",
	    component: AttendeeDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/organizers',
		name: 'Organizers',
		layout: "dashboard",
		component: Organizers,
	},
	{
	    path: '/organizer/:organizerId', 
	    name: 'OrganizerDetail',
		layout: "dashboard",
	    component: OrganizerDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/sessions',
		name: 'Sessions',
		layout: "dashboard",
		component: Sessions,
	},
	{
	    path: '/session/:sessionId', 
	    name: 'SessionDetail',
		layout: "dashboard",
	    component: SessionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/tickets',
		name: 'Tickets',
		layout: "dashboard",
		component: Tickets,
	},
	{
	    path: '/ticket/:ticketId', 
	    name: 'TicketDetail',
		layout: "dashboard",
	    component: TicketDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/sponsors',
		name: 'Sponsors',
		layout: "dashboard",
		component: Sponsors,
	},
	{
	    path: '/sponsor/:sponsorId', 
	    name: 'SponsorDetail',
		layout: "dashboard",
	    component: SponsorDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/feedbacks',
		name: 'Feedbacks',
		layout: "dashboard",
		component: Feedbacks,
	},
	{
	    path: '/feedback/:feedbackId', 
	    name: 'FeedbackDetail',
		layout: "dashboard",
	    component: FeedbackDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/requests/quickadd',
		name: 'QuickAdd',
		layout: "dashboard",
		meta: {
			title: 'quickadd',
			sidebarMap: ['applications'],
			breadcrumbs: ['Requests', 'QuickAdd'],
		},
		component: () => import('../pages/QuickAdd.vue'),
	},
	{
		path: '/tables',
		name: 'Tables',
		layout: "dashboard",
		component: () => import('../views/Tables.vue'),
	},
	{
		path: '/billing',
		name: 'Billing',
		layout: "dashboard",
		component: () => import('../views/Billing.vue'),
	},
	{
		path: '/rtl',
		name: 'RTL',
		layout: "dashboard-rtl",
		meta: {
			layoutClass: 'dashboard-rtl',
		},
		component: () => import('../views/RTL.vue'),
	},
	{
		path: '/Profile',
		name: 'Profile',
		layout: "dashboard",
		meta: {
			layoutClass: 'layout-profile',
		},
		component: () => import('../views/Profile.vue'),
	},
	{
		path: '/sign-in',
		name: 'Sign-In',
		component: () => import('../views/Sign-In.vue'),
	},
	{
		path: '/sign-up',
		name: 'Sign-Up',
		meta: {
			layoutClass: 'layout-sign-up',
		},
		component: () => import('../views/Sign-Up.vue'),
	},
]

// Adding layout property from each route to the meta
// object so it can be accessed later.
function addLayoutToRoute( route, parentLayout = "default" )
{
	route.meta = route.meta || {} ;
	route.meta.layout = route.layout || parentLayout ;
	
	if( route.children )
	{
		route.children = route.children.map( ( childRoute ) => addLayoutToRoute( childRoute, route.meta.layout ) ) ;
	}
	return route ;
}

routes = routes.map( ( route ) => addLayoutToRoute( route ) ) ;

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes,
	scrollBehavior (to, from, savedPosition) {
		if ( to.hash ) {
			return {
				selector: to.hash,
				behavior: 'smooth',
			}
		}
		return {
			x: 0,
			y: 0,
			behavior: 'smooth',
		}
	}
})

export default router
