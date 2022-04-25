import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from 'Components/Pages/Home/Home';
import SideNavibar from 'Components/UI/Organisms/SideNavibar/SideNavibar';
import Box from '@mui/material/Box';
import Navbar from 'Components/UI/Organisms/Navbar/Navbar';
import Setting from 'Components/Pages/Setting/Setting';

export default function BasicLayout() {
  return (
    <>
      <SideNavibar />
      <Box
        sx={{ paddingLeft: 1, paddingTop: 3, paddingRight: 10, marginLeft: 35 }}
      >
        <Navbar />

        <Setting />

        <Home />
      </Box>
    </>
  );
}
