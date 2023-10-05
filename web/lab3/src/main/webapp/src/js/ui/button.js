export const renderButton = (id, text, onClick) =>
    $(`<button/>`, {id, text, on: {click: onClick}});
