import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from 'Components/Pages/Home/Home';
import SideNavibar from 'Components/UI/Organisms/SideNavibar/SideNavibar';
import Box from '@mui/material/Box';
import Navbar from 'Components/UI/Organisms/Navbar/Navbar';
import Setting from 'Components/Pages/Setting/Setting';
import Detail from 'Components/Pages/Detail/Detail';
import BuildDetail from 'Components/Pages/BuildDetail/BuildDetail';

export default function BasicLayout() {
  return (
    <>
      <SideNavibar />
      <Box
        sx={{ paddingLeft: 1, paddingTop: 3, paddingRight: 10, marginLeft: 35 }}
      >
        <Navbar />
        <Switch>
          <Route path="/builddetail">
            <BuildDetail />
          </Route>
          <Route path="/detail">
            <Detail />
          </Route>
          <Route path="/setting">
            <Setting />
          </Route>
          <Route path="/">
            <Home />
          </Route>
        </Switch>
      </Box>
    </>
  );
}
