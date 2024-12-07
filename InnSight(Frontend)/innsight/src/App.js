import React from 'react'
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom'
import Navbar from './component/common/Navbar'
import Footer from './component/common/Footer'
import Home from './component/home/Home'
import "./index.css"
import AllRoomsPage from './component/booking-rooms/AllRoomsPage'
import FindBookingPage from './component/booking-rooms/FindBookingPage'
import RoomDetailsPage from './component/booking-rooms/RoomDetailsPage'
import LoginPage from './component/auth/LoginPage'
import Register from './component/auth/Register'
import ProfilePage from './component/profile/ProfilePage'
import EditProfilePage from './component/profile/EditProfilePage'
import { ProtectedRoute, AdminRoute } from './service/Guard'
import AdminPage from './component/admin/AdminPage'
import ManageRoomsPage from './component/admin/ManageRoomsPage'
import ManageBookingsPage from './component/admin/ManageBookingsPage'
import EditBookingPage from './component/admin/EditBookingPage'
import EditRoomPage from './component/admin/EditRoomPage'
import AddRoomPage from './component/admin/AddRoomPage'




export default function App() {
  return (

    <BrowserRouter>
      <div className='app'>
        <Navbar />

        <div className='content'>

          <Routes>
            {/* User Routes */}
            <Route exact path="/login" element={<LoginPage />} />
            <Route path="/register" element={<Register />} />
            <Route path='/home' element={<Home />} />
            <Route path='/rooms' element={<AllRoomsPage />} />
            <Route path='/find-booking' element={<FindBookingPage />} />


            {/* Authenticated User Routes */}
            <Route path='/room-details-book/:roomId' element={<ProtectedRoute element={<RoomDetailsPage />} />} />
            <Route path='/profile' element={<ProtectedRoute element={<ProfilePage />} />} />
            <Route path='/edit-profile' element={<ProtectedRoute element={<EditProfilePage />} />} />




            {/* Admin Routes */}
            <Route path="/admin"
              element={<AdminRoute element={<AdminPage />} />}
            />
            <Route path="/admin/manage-rooms"
              element={<AdminRoute element={<ManageRoomsPage />} />}
            />
            <Route path="/admin/edit-room/:roomId"
              element={<AdminRoute element={<EditRoomPage />} />}
            />
            <Route path="/admin/add-room"
              element={<AdminRoute element={<AddRoomPage />} />}
            />
            <Route path="/admin/manage-bookings"
              element={<AdminRoute element={<ManageBookingsPage />} />}
            />
            <Route path="/admin/edit-booking/:bookingCode"
              element={<AdminRoute element={<EditBookingPage />} />}
            />




            <Route path="*" element={<Navigate to="/home" />} />

          </Routes>
        </div>
        <Footer />
      </div>
    </BrowserRouter>

  )
}
