import React from 'react'
import { useState ,useEffect} from 'react';
import Pagination from '../common/Pagination';
import { RoomResult } from '../common/RoomResult';
import { RoomSearch } from '../common/RoomSearch';
import ApiService from '../../service/ApiService';

export default function AllRoomsPage() {
    const [rooms, setRooms] = useState([]);
    const [filteredRooms, setFilteredRooms] = useState([]);
    const [roomTypes, setRoomTypes] = useState([]);
    const [selectedRoomType, setSelectedRoomType] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const [roomsPerPage] = useState(5);
  
    const handleSearchResult = (results) => {
      setRooms(results);
      setFilteredRooms(results);
    };
  
  
    useEffect(() => {
      const fetchRooms = async () => {
        try {
          const response = await ApiService.getAllRooms();
          const allRooms = response.rooms;
          console.log(allRooms)
          setRooms(allRooms);
          setFilteredRooms(allRooms);
        } catch (error) {
          console.error('Error fetching rooms:', error.message);
        }
      };
  
      const fetchRoomTypes = async () => {
        try {
          const types = await ApiService.getRoomTypes();
          setRoomTypes(types);
        } catch (error) {
          console.error('Error fetching room types:', error.message);
        }
      };
  
      fetchRooms();
      fetchRoomTypes();
    }, []);
  
    const handleRoomTypeChange = (e) => {
      setSelectedRoomType(e.target.value);
      filterRooms(e.target.value);
    };
  
    const filterRooms = (type) => {
      if (type === '') {
        setFilteredRooms(rooms);
      } else {
        const filtered = rooms.filter((room) => room.roomType === type);
        setFilteredRooms(filtered);
      }
      setCurrentPage(1);
    };

    const indexOfLastRoom = currentPage * roomsPerPage;
    const indexOfFirstRoom = indexOfLastRoom - roomsPerPage;
    const currentRooms = filteredRooms.slice(indexOfFirstRoom, indexOfLastRoom);
  
    const paginate = (pageNumber) => setCurrentPage(pageNumber);
  
    return (
      <div className='all-rooms'>
        <h2>All Rooms</h2>
        <div className='all-room-filter-div'>
          <label>Filter by Room Type:</label>
          <select value={selectedRoomType} onChange={handleRoomTypeChange}>
            <option value="">All</option>
            {roomTypes.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>
        
        <RoomSearch handleSearchResult={handleSearchResult} />
        <RoomResult roomSearchResults={currentRooms} />
  
        <Pagination
          roomsPerPage={roomsPerPage}
          totalRooms={filteredRooms.length}
          currentPage={currentPage}
          paginate={paginate}
        />
      </div>
    );

}
