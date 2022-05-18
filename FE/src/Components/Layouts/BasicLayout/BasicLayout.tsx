import React, { useEffect, useState, Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';
import { useHistory } from 'react-router';
import Box from '@mui/material/Box';
import SideNavibar from 'Components/UI/Organisms/SideNavibar/SideNavibar';
import Navbar from 'Components/UI/Organisms/Navbar/Navbar';
import Cube from 'Components/UI/Atoms/Cube/Cube';
import LodingBar from 'Components/Pages/LodingBar/LodingBar';

const HomePage = lazy(() => import('Components/Pages/Home/Home'));
const DetailPage = lazy(() => import('Components/Pages/Detail/Detail'));
const SettingPage = lazy(() => import('Components/Pages/Setting/Setting'));
const StateDetailPage = lazy(
  () => import('Components/Pages/StateDetail/StateDetail'),
);

const transitionStyle = {
  transitionDuration: '0.2s',
  transitionProperty: 'all',
};

export default function BasicLayout() {
  const history = useHistory();
  const [open, setOpen] = useState(true);
  const loginInfo = sessionStorage.getItem('login');

  const handleMiniSidenav = () => {
    if (window.innerWidth > 1200) setOpen(true);
    else setOpen(false);
  };

  window.addEventListener('resize', handleMiniSidenav);

  useEffect(() => {
    if (!loginInfo) {
      history.push('/login');
    }
    return () => {
      setOpen(true);
    };
  }, [loginInfo]);

  return (
    <Box sx={{ zIndex: 1 }}>
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
        <Suspense fallback={<LodingBar />}>
          <Switch>
            <Route
              path="/state/:buildStateId"
              component={StateDetailPage}
              exact
            />
            <Route path="/detail/:projectId" component={DetailPage} exact />
            <Route path="/setting/:projectId" component={SettingPage} exact />
            <Route path="/" component={HomePage} exact />
          </Switch>
        </Suspense>
      </Box>
    </Box>
  );
}
