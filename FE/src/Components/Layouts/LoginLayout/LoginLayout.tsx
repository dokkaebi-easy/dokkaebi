import { useState } from 'react';
import Box from '@mui/material/Box';
import SignUp from 'Components/Pages/SignUp/SignUp';
import styled from '@emotion/styled';
import { Grid } from '@mui/material/';
import Login from '../../Pages/Login/Login';
import LoginImage from '../../../assets/loginimage.jpg';

function LoginLayout() {
  const [viewLogin, setViewLogin] = useState(true);
  const handleState = (data: any) => {
    setViewLogin(data);
  };

  return (
    <div>
      <ImgDiv>
        <LoginImg src={LoginImage} />
        {viewLogin ? <Login /> : <SignUp />}
      </ImgDiv>
    </div>
  );
}

export default LoginLayout;

const LoginImg = styled.img`
  width: 800px;
  margin-top: 30px;
`;

const ImgDiv = styled.div`
  display: flex;
`;
