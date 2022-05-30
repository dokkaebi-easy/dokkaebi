import React, { useEffect, useState, Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';
import Box from '@mui/material/Box';
import LoadingBar from 'Components/Pages/LoadingBar/LoadingBar';
import styled from 'styled-components';

const LoginPage = lazy(() => import('Components/Pages/Login/Login'));
const SignUpPage = lazy(() => import('Components/Pages/SignUp/SignUp'));

export default function AccessLayout() {
  return (
    <Box>
      <Suspense fallback={<LoadingBar />}>
        <Switch>
          <ContainerLog>
            <ContainerWave>
              <Route path="/access/signup" component={SignUpPage} exact />
              <Route path="/access/login" component={LoginPage} exact />
            </ContainerWave>
          </ContainerLog>
        </Switch>
      </Suspense>
    </Box>
  );
}

const ContainerLog = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url(/assets/blurry-gradient-haikei.svg);
  background-size: cover;
`;
const ContainerWave = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url(/assets/wave-haikei.svg);
  background-size: cover;
`;
