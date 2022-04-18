import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Home from 'Components/Pages/Home/Home';
import SideNavibar from 'Components/UI/Organisms/SideNavibar/SideNavibar';
import Box from '@mui/material/Box';

export default function HomeLayout() {
  return (
    <>
      <SideNavibar />
      <Box sx={{ paddingLeft: 35 }}>
        <BrowserRouter>
          <Switch>
            <Route path="/">
              <Home />
            </Route>
          </Switch>
        </BrowserRouter>
      </Box>
    </>
  );
}
