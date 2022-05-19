/* eslint-disable react/style-prop-object */
import React from 'react';
import styled, { keyframes } from 'styled-components';

interface LodingProps {
  number: number;
}

export default function LodingBar() {
  return (
    <LoaderBoxstyled>
      <LodingBoxstyled>
        <Lodingstyled number={1} />
        <Lodingstyled number={2} />
        <Lodingstyled number={3} />
        <Lodingstyled number={4} />
        <Lodingstyled number={5} />
        <Lodingstyled number={6} />
        <Lodingstyled number={7} />
        <Lodingstyled number={8} />
        <Lodingstyled number={9} />
        <Lodingstyled number={10} />
        <Lodingstyled number={11} />
        <Lodingstyled number={12} />
        <Lodingstyled number={13} />
        <Lodingstyled number={14} />
        <Lodingstyled number={15} />
        <Lodingstyled number={16} />
        <Lodingstyled number={17} />
        <Lodingstyled number={18} />
        <Lodingstyled number={19} />
        <Lodingstyled number={20} />
      </LodingBoxstyled>
    </LoaderBoxstyled>
  );
}

const animateBg = keyframes`
  0% {
    filter: hue-rotate(0deg);
  }
  100% {
    filter: hue-rotate(360deg);
  }
`;

const LoaderBoxstyled = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 85vh;
  animation: ${animateBg} 10s linear infinite;
`;

const LodingBoxstyled = styled.div`
  position: relative;
  width: 120px;
  height: 120px;
`;

const animate = keyframes`
  0% {
  transform: scale(0);
  }
  80%,100% {
  transform: scale(1);
  }
`;

const Lodingstyled = styled.span<LodingProps>`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  transform: rotate(calc(18deg * ${(props) => props.number}));

  &:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 15px;
    height: 15px;
    border-radius: 50%;
    background: #888;
    box-shadow: 0 0 10px #555 0 0 20px #555, 0 0 40px #555, 0 0 60px #555,
      0 0 80px #555, 0 0 100px #555;
    animation: ${animate} 2s linear infinite;
    animation-delay: calc(0.1s * ${(props) => props.number});
  }
`;
