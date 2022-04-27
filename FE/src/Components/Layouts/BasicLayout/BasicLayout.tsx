import React, { useEffect, useState } from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from 'Components/Pages/Home/Home';
import SideNavibar from 'Components/UI/Organisms/SideNavibar/SideNavibar';
import Box from '@mui/material/Box';
import Navbar from 'Components/UI/Organisms/Navbar/Navbar';
import Setting from 'Components/Pages/Setting/Setting';
import Detail from 'Components/Pages/Detail/Detail';
import BuildDetail from 'Components/Pages/BuildDetail/BuildDetail';

const transitionStyle = {
  transitionDuration: '0.2s',
  transitionProperty: 'all',
};

export default function BasicLayout() {
  const [open, setOpen] = useState(true);

  const handleMiniSidenav = () => {
    if (window.innerWidth > 1200) setOpen(true);
    else setOpen(false);
  };

  window.addEventListener('resize', handleMiniSidenav);

  useEffect(() => {
    return () => {
      setOpen(true);
    };
  }, []);

  return (
    <Box sx={{ fontFamily: 'Jua, sans-serif' }}>
      <Box
        style={
          open
            ? {
                ...transitionStyle,
                opacity: 1,
                transform: 'translate(0px,0)',
                position: 'fixed',
              }
            : {
                ...transitionStyle,
                opacity: 0,
                transform: 'translate(-200px,0)',
              }
        }
      >
        <SideNavibar />
      </Box>
      <Box
        sx={{ paddingTop: 3, paddingX: 3, marginLeft: 35 }}
        style={
          open ? { ...transitionStyle } : { ...transitionStyle, marginLeft: 0 }
        }
      >
        <Navbar />
        <Switch>
          <Route path="/builddetail/">
            <BuildDetail />
          </Route>
          <Route path="/detail/:projectId">
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
    </Box>
  );
}
