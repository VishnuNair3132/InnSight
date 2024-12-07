import axios from "axios"

export default class ApiService {

    static BASE_URL ="http://localhost:8080";



    static getHeaders() {
        const token = localStorage.getItem("token");
        console.log(token)
        return {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        };
    }

    static async registerUser(registration){
        const response = await axios.post(`${this.BASE_URL}/auth/register`,registration)
     
        return response.data;
    
    }

    static async loginUser(loginDetails){
        const response = await axios.post(`${this.BASE_URL}/auth/login`,loginDetails)
        return response.data
    }


    static async getAllUser(){
        const response = await axios.get(`${this.BASE_URL}/users/all`,{
            headers:this.getHeaders()
        })

        return response.data;
    }


    static async getUserById(userId){
        const response = await axios.get(`${this.BASE_URL}/users/get-by-id/${userId}`,{
            headers:this.getHeaders()
        })
        return response.data;

    }

    static async deleteUser(userId){
        const response = await axios.delete(`${this.BASE_URL}/users/delete/${userId}`,{
            headers:this.getHeaders()
        })
        return response.data;
    }

    static async getLoggedInUser(){
        const response = await axios.get(`${this.BASE_URL}/users/get-logged-in-profile-info`,{
            headers:this.getHeaders()
        })
        return response.data;
    }

    static async getUserBookings(userId){
        const response = await axios.get(`${this.BASE_URL}/users/get-user-bookings/${userId}`,{
            headers:this.getHeaders()
        })
        return response.data;
    }


    //Room Api Calling Service



    static async addRoom(formData){
        const response = await axios.post(`${this.BASE_URL}/rooms/add`,formData,{
            headers:{
                ...this.getHeaders(),
                "Content-Type":"multipart/form-data"
            }
        })
        return response.data
    }

    

    static async getAllRooms(){
        const response = await axios.get(`${this.BASE_URL}/rooms/all`)
        return response.data
    }
    






    static async getRoomTypes(){
        const response = await axios.get(`${this.BASE_URL}/rooms/types`)
          return response.data
        
    }

    static async getRoomById(roomId){
        const response = await axios.get(`${this.BASE_URL}/rooms/room-by-id/${roomId}`,
            {headers:this.getHeaders()})

        return response.data
    }


    static async getAllAvailableRooms(){
        const response = await axios.get(`${this.BASE_URL}/rooms/all-available-rooms`)

        return response.data

    }

    static async getAvailableRoomsByDateAndType(checkInDate,checkOutDate,roomType){

        const response =await axios.get(`${this.BASE_URL}/rooms/available-rooms-by-date-and-type?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${roomType}`)

        return response.data

        console.log(response.data)
    }


    static async updateRoom(roomId,formData){
        const response = await axios.put(`${this.BASE_URL}/rooms/update/${roomId}`,formData,{
            headers:{
                ...this.getHeaders(),
                'Content-Type':"multipart/form-data"
            }
        })

        return response.data
    }

    static async deleteRoom(roomId){
        const response =await axios.delete(`${this.BASE_URL}/rooms/delete/${roomId}`,{
            headers:this.getHeaders()
        })

        return response.data
    }

    //Booking Api Calling Functions


    static async bookARoom(userId,roomId,bookingObj){
        const response = await axios.post(`${this.BASE_URL}/bookings/book-room/${userId}/${roomId}`,bookingObj,{
            headers:this.getHeaders()
        })

        return response.data
    }

    static async getAllBookings(){
        const response = await axios.get(`${this.BASE_URL}/bookings/all`,{
            headers:this.getHeaders()
        })
        return response.data
    }

    static async getByBookingConfrimationCode(confirmationCode){
        const response = await axios.get(`${this.BASE_URL}/bookings/get-by-confirmation-code/${confirmationCode}`)

        return response.data
    }

    static async cancelBooking(bookingId){
        const response =await axios.delete(`${this.BASE_URL}/bookings/cancel/${bookingId}`,{
            headers:this.getHeaders()
        })
        return response.data
    }

    static logout(){
        localStorage.removeItem('token')
        localStorage.removeItem('role')
    }

    static isAdmin(){
        const role = localStorage.getItem('role')
        return role === 'ADMIN'
    }

    static isAuthenticated(){
        const token = localStorage.getItem('token')
        return !!token
    }


    static isUser(){
        const role = localStorage.getItem('role')
        return role === 'USER'
    }
};
