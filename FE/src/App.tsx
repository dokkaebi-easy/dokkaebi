import React, { Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';
import CssBaseline from '@mui/material/CssBaseline';

const LoginPage = lazy(() => import('Components/Pages/Login/Login'));
const SignUpPage = lazy(() => import('Components/Pages/SignUp/SignUp'));
const HomeLayoutPage = lazy(
  () => import('Components/Layouts/BasicLayout/BasicLayout'),
);

function App() {
  return (
    <>
      <CssBaseline />
      <Suspense fallback={<div>로딩 중...</div>}>
        <Switch>
          <Route path="/signup" component={SignUpPage} exact />
          <Route path="/login" component={LoginPage} exact />
          <Route path="/" component={HomeLayoutPage} />
        </Switch>
      </Suspense>
    </>
  );
}

export default App;
