import React, { useState, useEffect } from 'react'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import ApiService from '../../service/ApiService'


export const RoomSearch = ({ handleSearchResult }) => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [roomType, setRoomType] = useState('');
  const [roomTypes, setRoomTypes] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchRoomTypes = async () => {
      try {
        const types = await ApiService.getRoomTypes();
        setRoomTypes(types);
        console.log(types)
      } catch (error) {
        console.error('Error fetching room types:', error.message);
      }
    };
    fetchRoomTypes();
  }, []);

  console.log(roomTypes);

  const showError = (message, timeout = 5000) => {
    setError(message);
    setTimeout(() => {
      setError('');
    }, timeout);
  };

  const handleInternalSearch = async () => {
    if (!startDate || !endDate || !roomType) {
      showError('Please select all fields');
      return false;
    }
    try {
      const formattedStartDate = startDate ? startDate.toISOString().split('T')[0] : null;
      const formattedEndDate = endDate ? endDate.toISOString().split('T')[0] : null;
      const response = await ApiService.getAvailableRoomsByDateAndType(formattedStartDate, formattedEndDate, roomType);
      console.log(response)
      if (response.statusCode === 200) {
        if (response.rooms.length === 0) {
          showError('Room not currently available for this date range on the selected rom type.');
          return
        }
        handleSearchResult(response.rooms);
        setError('');
      }
    } catch (error) {
      showError("Unown error occured: " + error.response);
    }
  };






  return (
    <>
      <section>
        <div className="search-container">
          <div className="search-field">
            <label>Check-in Date</label>
            <DatePicker
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              dateFormat="dd/MM/yyyy"
              placeholderText="Select Check-in Date"
            />
          </div>
          <div className="search-field">
            <label>Check-out Date</label>
            <DatePicker
              selected={endDate}
              onChange={(date) => setEndDate(date)}
              dateFormat="dd/MM/yyyy"
              placeholderText="Select Check-out Date"
            />
          </div>

          <div className="search-field">
            <label>Room Type</label>
            <select value={roomType} onChange={(e) => setRoomType(e.target.value)}>
              <option disabled value="">
                Select Room Type
              </option>
              {roomTypes.map((roomType) => (
                <option key={roomType} value={roomType}>
                  {roomType}
                </option>
              ))}
            </select>
          </div>
          <button className="home-search-button" onClick={handleInternalSearch}>
            Search Rooms
          </button>
        </div>
        {error && <p className="error-message">{error}</p>}
      </section>
    </>
  )
}
